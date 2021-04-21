package xenoteo.com.github;

import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.data.Stat;

public class DataMonitor implements Watcher, StatCallback {
    private final ZooKeeper zk;
    private final String znode;
    private final Watcher chainedWatcher;
    private final DataMonitorListener listener;

    public DataMonitor(ZooKeeper zk, String znode, Watcher chainedWatcher, DataMonitorListener listener)
            throws KeeperException, InterruptedException {
        this.zk = zk;
        this.znode = znode;
        this.chainedWatcher = chainedWatcher;
        this.listener = listener;
        zk.exists(znode, true, this, null);
        try {
            zk.getChildren(znode, this);
        } catch (KeeperException | InterruptedException e) {
            System.out.println("No node for /z");
        }
    }

    public interface DataMonitorListener {
        /**
         * The existence status of the node has changed.
         */
        void exists();

        /**
         * The ZooKeeper session is no longer valid.
         *
         * @param rc  the ZooKeeper reason code
         */
        void closing(int rc);
    }

    public void process(WatchedEvent event) {
        String path = event.getPath();
        if (event.getType() == Event.EventType.None) {
            if (event.getState() == Event.KeeperState.Expired) {
                listener.closing(Code.SessionExpired);
            }
        } else if(event.getType() == Event.EventType.NodeChildrenChanged) {
            long descendantsNumber = countDescendants(znode);
            if (descendantsNumber != -1){
                System.out.printf("The number of descendants is %d\n", descendantsNumber);
            }
        } else if (path != null && path.equals(znode)) {
            zk.exists(znode, true, this, null);
        }
        if (chainedWatcher != null) {
            chainedWatcher.process(event);
        }
    }

    private long countDescendants(String path) {
        try {
            List<String> children = zk.getChildren(path, this);
            return children.size() + children.stream().mapToLong(child -> countDescendants(path + "/" + child)).sum();
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void processResult(int rc, String path, Object ctx, Stat stat) {
        switch (rc) {
            case Code.Ok -> listener.exists();
            case Code.NoNode, Code.SessionExpired, Code.NoAuth -> listener.closing(rc);
            default ->  zk.exists(znode, true, this, null);
        }
    }
}