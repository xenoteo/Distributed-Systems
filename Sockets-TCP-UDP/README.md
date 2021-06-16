# Sockets TCP/UDP
## [Homework](https://github.com/xenoteo/Distributed-Systems/tree/main/Sockets-TCP-UDP/src/xenoteo/com/github/homework)
### About
The chat application. The server accepts messages from each client and sends them to all the others. 
The client can choose whether to send a message using TCP, UDP or multicast. By default, messages are sent via TCP.

### Execution
No extra requirements are needed. You can run [server](src/xenoteo/com/github/homework/server/Server.java) and 
[client](src/xenoteo/com/github/homework/client/Client.java) class straightway.  
If you use IntelliJ and there are dependencies that are not seen go to `File` -> `Invalidate Caches / Restart...`.

## [Lab](https://github.com/xenoteo/Distributed-Systems/tree/main/Sockets-TCP-UDP/src/xenoteo/com/github/lab)
- [task 1](https://github.com/xenoteo/Distributed-Systems/tree/main/Sockets-TCP-UDP/src/xenoteo/com/github/lab/task1) -
  the implementation of simple bidirectional communication between a server and a client both written in Java, using UDP.
- [task 2](https://github.com/xenoteo/Distributed-Systems/tree/main/Sockets-TCP-UDP/src/xenoteo/com/github/lab/task2) -
  the implementation of simple bidirectional communication between a server written in Java and a client written in Python, using UDP.
- [task 3](https://github.com/xenoteo/Distributed-Systems/tree/main/Sockets-TCP-UDP/src/xenoteo/com/github/lab/task3) -
  the implementation of sending a numeric value by a client to a server;
  a server is written in Java and a client is written in Python, using UDP.
- [task 4](https://github.com/xenoteo/Distributed-Systems/tree/main/Sockets-TCP-UDP/src/xenoteo/com/github/lab/task4) -
  the implementation of server identifying in which language is written a client that sent a message.