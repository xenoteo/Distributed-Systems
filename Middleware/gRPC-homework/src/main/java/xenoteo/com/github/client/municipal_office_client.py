from __future__ import print_function
import logging
import os
import sys

import grpc
import municipal_office_pb2
import municipal_office_pb2_grpc
from datetime import datetime
import time
import sqlite3


def time_now():
    return datetime.now().strftime("%H:%M:%S")


def issue_string(issue_type):
    if issue_type == municipal_office_pb2.ISSUE_TYPE_PASSPORT:
        return "passport issue"
    elif issue_type == municipal_office_pb2.ISSUE_TYPE_CITIZENSHIP:
        return "citizenship issue"
    elif issue_type == municipal_office_pb2.ISSUE_TYPE_RESIDENCE:
        return "residence issue"
    else:
        return "unknown issue"


def is_not_present(client_id, conn):
    cursor = conn.execute("select * from clients where id = ?", (client_id,))
    return cursor.fetchone() is None


def is_waiting(client_id, conn):
    cursor = conn.execute("select waiting from clients where id = ?", (client_id,))
    waiting = 0
    for row in cursor:
        waiting = row[0]
    return waiting


def is_not_collected(client_id, conn):
    cursor = conn.execute("select collected from clients where id = ?", (client_id,))
    collected = 1
    for row in cursor:
        collected = row[0]
    return collected == 0


def get_last_response(client_id, conn):
    cursor = conn.execute("select response from clients where id = ?", (client_id,))
    response = ""
    for row in cursor:
        response = row[0]
    return response


def call_municipal_office(stub, name, client_id, issue_type):
    print(f"[{time_now()}] sending a request for a {issue_string(issue_type)}...")
    try:
        response = stub.Commission(municipal_office_pb2.IssueArguments(name=name, id=client_id, type=issue_type))
        print(f"[{time_now()}] receiving a municipal office response: {response.answer}", end="")
    except grpc._channel._InactiveRpcError:
        print("Municipal office is out of order. Request is not sent")


def wait_for_prev_issue(client_id, conn):
    while is_waiting(client_id, conn):
        print(f"[{time_now()}] waiting for the previous issue to be handled...")
        time.sleep(2)
    print(f"[{time_now()}] receiving a municipal office response: {get_last_response(client_id, conn)}", end="")
    conn.execute("update clients set collected = ? where id = ?", (1, client_id))
    conn.commit()


def run():
    conn = sqlite3.connect('../../../../../../../mo.db')

    name = input("Enter a client name: ")
    client_id = int(input("Enter a client ID: "))

    if is_not_present(client_id, conn):
        conn.execute("insert into clients (id, name, waiting, response, collected) values (?, ?, ?, ?, ?)",
                     (client_id, name, 0, None, 1))
        conn.commit()

    if is_waiting(client_id, conn) or is_not_collected(client_id, conn):
        wait_for_prev_issue(client_id, conn)

    channel = grpc.insecure_channel('localhost:50051')
    stub = municipal_office_pb2_grpc.MunicipalOfficeStub(channel)

    choice = '0'
    while choice != 'q':
        choice = input("Choose an issue to send request for "
                       "(1 for a passport, 2 for a citizenship, 3 for a residence, q to quit): ")
        if choice == '1':
            call_municipal_office(stub, name, client_id, municipal_office_pb2.ISSUE_TYPE_PASSPORT)
        elif choice == '2':
            call_municipal_office(stub, name, client_id, municipal_office_pb2.ISSUE_TYPE_CITIZENSHIP)
        elif choice == '3':
            call_municipal_office(stub, name, client_id, municipal_office_pb2.ISSUE_TYPE_RESIDENCE)
        elif choice != 'q':
            print("Bad argument provided.")

    conn.close()


if __name__ == '__main__':
    logging.basicConfig()
    try:
        run()
    except KeyboardInterrupt:
        print('A client is interrupted')
        try:
            sys.exit(0)
        except SystemExit:
            os._exit(0)
