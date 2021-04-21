package xenoteo.com.github;

import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.data.Stat;

/**
 * The class monitoring the data in the ZooKeeper tree.
 */
public class DataMonitor implements Watcher, StatCallback {

    /**
     * The ZooKeeper object.
     */
    private final ZooKeeper zk;

    /**
     * The znode to watch.
     */
    private final String znode;

    /**
     * The watcher.
     */
    private final Watcher chainedWatcher;

    /**
     * The data monitor listener.
     */
    private final DataMonitorListener listener;

    public DataMonitor(ZooKeeper zk, String znode, Watcher chainedWatcher, DataMonitorListener listener) {
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

    /**
     * The interface for implementing data monitor listener.
     */
    public interface DataMonitorListener {

        /**
         * Reacts when the existence status of the node has changed.
         */
        void exists();

        /**
         * Reacts when the ZooKeeper session is no longer valid.
         *
         * @param rc  the ZooKeeper reason code
         */
        void closing(int rc);
    }

    @Override
    public void process(WatchedEvent event) {
        String path = event.getPath();
        // if the session expired, inform the listener about closing
        if (event.getType() == Event.EventType.None && event.getState() == Event.KeeperState.Expired) {
            listener.closing(Code.SessionExpired);
        }
        // if new children was created or deleted, print the new number of children
        else if (event.getType() == Event.EventType.NodeChildrenChanged) {
            long descendantsNumber = countDescendants(znode);
            if (descendantsNumber != -1){
                System.out.printf("The number of descendants is %d\n", descendantsNumber);
            }
        }
        // if the change related to the followed znode path, check its stat
        else if (path != null && path.equals(znode)) {
            zk.exists(znode, true, this, null);
        }

        if (chainedWatcher != null) {
            chainedWatcher.process(event);
        }
    }

    /**
     * Counts the number of descendants starting form the provided path.
     *
     * @param path  the path to start counting from
     * @return the number of descendants
     */
    private long countDescendants(String path) {
        try {
            List<String> children = zk.getChildren(path, this);
            return children.size() + children.stream().mapToLong(child -> countDescendants(path + "/" + child)).sum();
        } catch (KeeperException | InterruptedException e) {
            System.out.printf("No node for %s\n", path);
            return -1;
        }
    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        switch (rc) {
            // if everything is ok, inform the listener about it
            case Code.Ok -> listener.exists();
            // if there is no node, session expired or no auth, inform listener about closing
            case Code.NoNode, Code.SessionExpired, Code.NoAuth -> listener.closing(rc);
            // check the watched znode stat
            default -> zk.exists(znode, true, this, null);
        }
    }
}