from __future__ import print_function
import logging
import grpc
import municipal_office_pb2
import municipal_office_pb2_grpc
from datetime import datetime


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


def call_municipal_office(stub, name, issue_type):
    print(f"[{time_now()}] sending a request for a {issue_string(issue_type)}...")
    try:
        response = stub.Commission(municipal_office_pb2.IssueArguments(
            name=name, type=issue_type))
        print(f"[{time_now()}] receiving a municipal office response: {response.answer}", end="")
    except grpc._channel._InactiveRpcError:
        print("Municipal office is out of order")


def run():
    name = input("Enter your name: ")
    channel = grpc.insecure_channel('localhost:50051')
    stub = municipal_office_pb2_grpc.MunicipalOfficeStub(channel)
    choice = '0'
    while choice != 'q':
        choice = input("Choose an issue to send request for "
                       "(1 for a passport, 2 for a citizenship, 3 for a residence, q to quit): ")
        if choice == '1':
            call_municipal_office(stub, name, municipal_office_pb2.ISSUE_TYPE_PASSPORT)
        elif choice == '2':
            call_municipal_office(stub, name, municipal_office_pb2.ISSUE_TYPE_CITIZENSHIP)
        elif choice == '3':
            call_municipal_office(stub, name, municipal_office_pb2.ISSUE_TYPE_RESIDENCE)
        elif choice != 'q':
            print("Bad argument provided.")


if __name__ == '__main__':
    logging.basicConfig()
    run()
