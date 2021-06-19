from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.protocol import TCompactProtocol
from thrift.protocol import TJSONProtocol
from thrift_client import DataGenerator
import sys


def print_error():
    """A function printing an error when bad arguments provided"""

    print("Bad arguments provided.")
    print("The program needs a serialisation method to be provided as program argument: binary | json | compact")


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
    A simple script running memory test, that is counting how many bytes are written to TMemoryBuffer
    using different serialisation methods while sending 1000000 elements.
    """

    if len(sys.argv) < 2:
        print_error()
        exit(1)

    trans = TTransport.TMemoryBuffer()

    protocol_type = sys.argv[1]
    proto = read_protocol(protocol_type, trans)
    if proto is None:
        print_error()
        exit(1)

    data = DataGenerator().generate_list(1000000)

    for x in data:
        proto.writeI32(x)

    print(f"Using {protocol_type} and sending 1000000 elements wrote {trans.cstringio_buf.tell()} "
          f"bytes to the TMemoryBuffer")
