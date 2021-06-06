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


if __name__ == '__main__':
    handler = TransferHandler()
    processor = Transfer.Processor(handler)
    transport = TSocket.TServerSocket(host='127.0.0.1', port=9080)
    transport_factory = TTransport.TBufferedTransportFactory()

    # protocol_factory = TBinaryProtocol.TBinaryProtocolFactory()
    # protocol_factory = TJSONProtocol.TJSONProtocolFactory()
    protocol_factory = TCompactProtocol.TCompactProtocolFactory()

    server = TServer.TSimpleServer(processor, transport, transport_factory, protocol_factory)

    print('Starting the server...')

    try:
        server.serve()
    except KeyboardInterrupt:
        print('A server is interrupted')
        try:
            sys.exit(0)
        except SystemExit:
            os._exit(0)

