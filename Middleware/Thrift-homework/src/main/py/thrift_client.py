import time
import sys

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.protocol import TCompactProtocol
from thrift.protocol import TJSONProtocol
from src.main.py.gen_py.transfer import Transfer


class DataGenerator:
    """A class generating data structures filled with the provided number of elements."""

    def __init__(self):
        self.log = {}

    def generate_list(self, size):
        """
        A function generating a list filled with the provided number of elements.

        :param size: the size of the list
        :return: the list filled with the provided number of elements
        """

        data = []
        for i in range(size):
            data.append(i)
        return data

    def generate_set(self, size):
        """
        A function generating a set filled with the provided number of elements.

        :param size: the size of the set
        :return: the set filled with the provided number of elements
        """

        data = set()
        for i in range(size):
            data.add(i)
        return data

    def generate_map(self, size):
        """
        A function generating a map filled with the provided number of elements.

        :param size: the size of the map
        :return: the map filled with the provided number of elements
        """

        data = {}
        for i in range(size):
            data[i] = i
        return data


def print_error():
    """A function printing an error when bad arguments provided"""

    print("Bad arguments provided.")
    print("The client needs a serialisation method to be provided as program argument: binary | json | compact")


def read_protocol(protocol_type, trans):
    """
    A function returning the protocol based on an argument provided.

    :param protocol_type: the protocol type
    :param trans: the memory buffer
    :return: the protocol of the required type
    """

    if protocol_type == "binary":
        return TBinaryProtocol.TBinaryProtocol(trans)
    elif protocol_type == "json":
        return TJSONProtocol.TJSONProtocol(trans)
    elif protocol_type == "compact":
        return TCompactProtocol.TCompactProtocol(trans)
    return None


if __name__ == '__main__':
    """
    The simple Thrift client sending data of different size, stored in different datastructures, 
    using different Thrift serialisation methods to the Thrift simple server.
    """

    if len(sys.argv) < 2:
        print_error()
        exit(1)
    try:
        transport = TSocket.TSocket('localhost', 9080)
        transport = TTransport.TBufferedTransport(transport)

        protocol_type = sys.argv[1]
        protocol = read_protocol(protocol_type, transport)
        if protocol is None:
            print_error()
            exit(1)

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
