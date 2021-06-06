from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.protocol import TCompactProtocol
from thrift.protocol import TJSONProtocol
from thrift_client import DataGenerator


trans = TTransport.TMemoryBuffer()

proto = TBinaryProtocol.TBinaryProtocol(trans)
# proto = TJSONProtocol.TJSONProtocol(trans)
# proto = TCompactProtocol.TCompactProtocol(trans)

generator = DataGenerator()
data = generator.generate_list(1000000)
# data = generator.generate_set(1000000)

for x in data:
    proto.writeI32(x)

print(f"Wrote {trans.cstringio_buf.tell()} bytes to the TMemoryBuffer")

