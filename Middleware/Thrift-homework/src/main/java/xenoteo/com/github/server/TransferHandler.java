package xenoteo.com.github.server;

import org.apache.thrift.TException;
import xenoteo.com.github.thrift.gen.Transfer;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TransferHandler implements Transfer.Iface {

    @Override
    public long transferList(List<Integer> data) throws TException {
        System.out.printf("Received a list of %d elements\n", data.size());
        return System.currentTimeMillis();
    }

    @Override
    public long transferSet(Set<Integer> data) throws TException {
        System.out.printf("Received a set of %d elements\n", data.size());
        return System.currentTimeMillis();
    }

    @Override
    public long transferMap(Map<Integer, Integer> data) throws TException {
        System.out.printf("Received a map of %d elements\n", data.size());
        return System.currentTimeMillis();
    }
}
