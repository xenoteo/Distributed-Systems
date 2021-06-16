# ZooKeeper
Homework

## About
The task is inspired by [ZooKeeper Java Example](https://zookeeper.apache.org/doc/r3.4.13/javaExample.html).  
This is a very simple watch client. This ZooKeeper client watches a ZooKeeper node for changes and 
responds to by starting or stopping a program.

### Client requirements
- If the node of name `/z` is created, then the graphical app is executed. The app can be any, 
provided as a program argument.
- If the znode disappears, the client kills the executable.
- After each addition or removal of any znode descendants the current number of `/z`-znode descendants is shown.
- Additionally, the app should allow to view the whole znode-tree.
- The app should run in "Replicated ZooKeeper".

## Execution
*Note: the following configuration is provided for the Linux OS.*

Run the [main program](src/xenoteo/com/github/Executor.java) 
with two arguments: `<host port> <the command to execute>`  
Example argument list: `127.0.0.1:2181 kate`   
If you use IntelliJ and there are dependencies that are not seen go to `File` -> `Invalidate Caches / Restart...`.

To run Zookeeper servers and clients go to `apache-zookeeper-3.6.1-bin/bin` folder. 
Run three servers using three configuration files `zoo1.cfg`, `zoo3.cfg` and `zoo3.cfg`, 
which are placed in the folder `apache-zookeeper-3.6.1-bin/conf`. To do this run 
the following commands:  
`sudo ./zkServer.sh start ../conf/zoo1.cfg`  
`sudo ./zkServer.sh start ../conf/zoo2.cfg`  
`sudo ./zkServer.sh start ../conf/zoo3.cfg`  
After you started servers, run three clients in different terminal tabs on these 
servers with the following commands:  
`sudo ./zkCli.sh -server localhost:2888:3888`  
`sudo ./zkCli.sh -server localhost:2889:3889`  
`sudo ./zkCli.sh -server localhost:2890:3890`  

## Some Zookeeper commands
After you started clients on servers, you can run in a terminal of each of them the following commands:
- `help` to list all available commands
- `ls /` to view znode list
- `create /znode-name` to create new znode
- `delete /znode-name` to delete a znode
- `deleteall /znode-name` to delete a znode with all its descendants