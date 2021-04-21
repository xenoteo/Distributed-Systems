package xenoteo.com.github;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class Executor implements Watcher, Runnable, DataMonitor.DataMonitorListener {
    private final DataMonitor dm;
    private final ZooKeeper zk;
    private final String znode;
    private final String[] exec;
    private Process child;

    public Executor(String hostPort, String znode, String[] exec)
            throws KeeperException, IOException, InterruptedException {
        this.znode = znode;
        this.exec = exec;
        zk = new ZooKeeper(hostPort, 10000, this);
        dm = new DataMonitor(zk, znode, null, this);
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Too few arguments. Example argument list: 127.0.0.1:2181 cmd.exe");
            System.exit(2);
        }
        String hostPort = args[0];
        String znode = "/z";
        String[] exec = new String[args.length - 1];
        System.arraycopy(args, 1, exec, 0, exec.length);
        try {
            new Executor(hostPort, znode, exec).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void process(WatchedEvent event) {
        dm.process(event);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("Enter 'tree' to se the tree and 'stop' to stop:");
            String line = scanner.nextLine();
            switch (line) {
                case "tree" -> show(znode, znode, 0);
                case "stop" -> {
                    running = false;
                    closing(0);
                }
            }
        }
    }

    private void show(String child, String path, int i) {
        try{
            List<String> children = zk.getChildren(path, dm);
            IntStream.rangeClosed(0, i).forEach(k -> System.out.print(" "));
            System.out.println(child);
            children.forEach(c -> show(c, path + "/" + c, i + 1));
        } catch (InterruptedException | KeeperException e) {
            System.out.println("No node for /z");
        }
    }

    public void closing(int rc) {
        synchronized (this) {
            System.out.println("Stopping exec");
            if (child != null) child.destroy();
        }
    }

    public void exists() {
        try {
            System.out.println("Starting exec");
            child = Runtime.getRuntime().exec(exec);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}