import time

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.protocol import TCompactProtocol
from thrift.protocol import TJSONProtocol
from src.main.py.gen_py.transfer import Transfer


class DataGenerator:
    def __init__(self):
        self.log = {}

    def generate_list(self, size):
        data = []
        for i in range(size):
            data.append(i)
        return data

    def generate_set(self, size):
        data = set()
        for i in range(size):
            data.add(i)
        return data

    def generate_map(self, size):
        data = {}
        for i in range(size):
            data[i] = i
        return data


if __name__ == '__main__':
    try:
        transport = TSocket.TSocket('localhost', 9080)
        transport = TTransport.TBufferedTransport(transport)

        # protocol = TBinaryProtocol.TBinaryProtocol(transport)
        # protocol = TJSONProtocol.TJSONProtocol(transport)
        protocol = TCompactProtocol.TCompactProtocol(transport)

        client = Transfer.Client(protocol)

        transport.open()
        generator = DataGenerator()
        running = True
        while running:
            choice = input("Choose a data structure to transfer (list, set or map; q to quit): ")
            if choice == "list" or choice == "set" or choice == "map":
                size = int(input("Choose a data structure size: "))
                sendTime = -1
                gotTime = 0
                if choice == "list":
                    data = generator.generate_list(size)
                    sendTime = int(round(time.time() * 1000))
                    gotTime = client.transferList(data)
                elif choice == "set":
                    data = generator.generate_set(size)
                    sendTime = int(round(time.time() * 1000))
                    gotTime = client.transferSet(data)
                elif choice == "map":
                    data = generator.generate_map(size)
                    sendTime = int(round(time.time() * 1000))
                    gotTime = client.transferMap(data)
                print(f"{choice} of {size} elements transfer time is {gotTime - sendTime} ms")
            elif choice == "q":
                running = False

        transport.close()

    except Thrift.TException as tx:
        print(tx.message)
