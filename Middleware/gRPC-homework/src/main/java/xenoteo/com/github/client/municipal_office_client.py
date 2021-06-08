import os
import sys
import grpc
import municipal_office_pb2, municipal_office_pb2_grpc
from datetime import datetime
from db_connector import DBConnector


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


def call_municipal_office(stub, name, client_id, issue_type, db):
    try:
        db.set_waiting(client_id, 1)
        print(f"[{time_now()}] sending a request for a {issue_string(issue_type)}...")
        response = stub.Commission(municipal_office_pb2.IssueArguments(name=name, id=client_id, type=issue_type))
        print(f"[{time_now()}] receiving a municipal office response: {response.answer}", end="")
        db.set_waiting(client_id, 0)
    except grpc._channel._InactiveRpcError:
        print("Municipal office is out of order. Request is not sent")


def run(db):
    name = input("Enter a client name: ")
    client_id = int(input("Enter a client ID: "))

    if db.is_not_present(client_id):
        db.add_new_client(client_id)

    channel = grpc.insecure_channel('localhost:50051')
    stub = municipal_office_pb2_grpc.MunicipalOfficeStub(channel)

    if db.is_waiting(client_id):
        print("handling the previous issue...")
        issue = db.get_prev_issue(client_id)
        if issue == 1:
            call_municipal_office(stub, name, client_id, municipal_office_pb2.ISSUE_TYPE_PASSPORT, db)
        elif issue == 2:
            call_municipal_office(stub, name, client_id, municipal_office_pb2.ISSUE_TYPE_CITIZENSHIP, db)
        elif issue == 3:
            call_municipal_office(stub, name, client_id, municipal_office_pb2.ISSUE_TYPE_RESIDENCE, db)

    choice = '0'
    while choice != 'q':
        choice = input("Choose an issue to send request for "
                       "(1 for a passport, 2 for a citizenship, 3 for a residence, q to quit): ")
        if choice == '1':
            db.set_issue(client_id, 1)
            call_municipal_office(stub, name, client_id, municipal_office_pb2.ISSUE_TYPE_PASSPORT, db)
        elif choice == '2':
            db.set_issue(client_id, 2)
            call_municipal_office(stub, name, client_id, municipal_office_pb2.ISSUE_TYPE_CITIZENSHIP, db)
        elif choice == '3':
            db.set_issue(client_id, 3)
            call_municipal_office(stub, name, client_id, municipal_office_pb2.ISSUE_TYPE_RESIDENCE, db)
        elif choice != 'q':
            print("Bad argument provided.")

    db.disconnect()


if __name__ == '__main__':
    connector = DBConnector()
    try:
        run(connector)
    except KeyboardInterrupt:
        print('A client is interrupted')
        connector.disconnect()
        try:
            sys.exit(0)
        except SystemExit:
            os._exit(0)
