import os
import sys
import time
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.protocol import TCompactProtocol
from thrift.protocol import TJSONProtocol
from thrift.server import TServer

from src.main.py.gen_py.transfer import Transfer


class TransferHandler:
    """A class responsible for handling data transfers."""

    def __init__(self):
        self.log = {}

    def transferList(self, data):
        print(f"Received a list of {len(data)} elements")
        return int(round(time.time() * 1000))

    def transferSet(self, data):
        print(f"Received a set of {len(data)} elements")
        return int(round(time.time() * 1000))

    def transferMap(self, data):
        print(f"Received a map of {len(data)} elements")
        return int(round(time.time() * 1000))


def print_error():
    """A function printing an error when bad arguments provided"""

    print("Bad arguments provided.")
    print("The server needs a serialisation method to be provided as program argument: binary | json | compact")


def read_protocol(protocol_type):
    """
    A function returning the protocol factory based on an argument provided.

    :param protocol_type: the protocol type
    :return: the protocol factory of the required type
    """

    if protocol_type == "binary":
        return TBinaryProtocol.TBinaryProtocolFactory()
    elif protocol_type == "json":
        return TJSONProtocol.TJSONProtocolFactory()
    elif protocol_type == "compact":
        return TCompactProtocol.TCompactProtocolFactory()
    return None


if __name__ == '__main__':
    """The simple Thrift server receiving data from the Thrift client."""

    if len(sys.argv) < 2:
        print_error()
        exit(1)

    handler = TransferHandler()
    processor = Transfer.Processor(handler)
    transport = TSocket.TServerSocket(host='127.0.0.1', port=9080)
    transport_factory = TTransport.TBufferedTransportFactory()

    protocol_type = sys.argv[1]
    protocol_factory = read_protocol(protocol_type)
    if protocol_factory is None:
        print_error()
        exit(1)

    server = TServer.TSimpleServer(processor, transport, transport_factory, protocol_factory)

    print('The server is started...')

    try:
        server.serve()
    except KeyboardInterrupt:
        print('A server is interrupted')
        try:
            sys.exit(0)
        except SystemExit:
            os._exit(0)

