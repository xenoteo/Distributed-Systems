package xenoteo.com.github;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryBuffer;
import xenoteo.com.github.client.DataGenerator;

import java.util.List;

public class MemoryTest {
    public static void main(String[] args) {
        MemoryTest memoryTest = new MemoryTest();
        memoryTest.test();
    }

    private void test(){
        try {
            TMemoryBuffer trans = new TMemoryBuffer(4096);

            TProtocol proto = new TBinaryProtocol(trans);
//            TProtocol proto = new TJSONProtocol(trans);
//            TProtocol proto = new TCompactProtocol(trans);

            DataGenerator generator = new DataGenerator();

            List<Integer> list = generator.generateList(1000000);
            for (int x : list){
                proto.writeI32(x);
            }

//            Set<Integer> set = generator.generateSet(1000000);
//            for (int x : set){
//                proto.writeI32(x);
//            }

//            Map<Integer, Integer> map = generator.generateMap(1000000);
//            for (Map.Entry<Integer, Integer> pair : map.entrySet()){
//                proto.writeI32(pair.getKey());
//                proto.writeI32(pair.getValue());
//            }

            System.out.println("Wrote " + trans.length() + " bytes to the TMemoryBuffer");
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}
