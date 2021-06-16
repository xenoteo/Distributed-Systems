# RabbitMQ
## Dependencies
- [Erlang](https://www.erlang.org/downloads)
- [RabbitMQ server](https://www.rabbitmq.com/download.html)
- Libraries from the [lib](https://github.com/xenoteo/Distributed-Systems/tree/main/RabbitMQ/lib) folder

## [Homework](https://github.com/xenoteo/Distributed-Systems/tree/main/RabbitMQ/src/xenoteo/com/github/homework)
### About
*You can also read the task description in [Polish](HOMEWORK_POLISH.md).*  
  
The communication between mountain squads and equipment supplier.  

Suppliers have their own constant list of products they supply, 
which is initialized in the beginning of their work.  

Squads can order any items.  

The prices of the same items are the same for all suppliers.  

Orders should be distributed among suppliers in a balanced manner.  

A given order may not go to more than one supplier.  

Orders are identified by the name of the team and the internal order number assigned by the supplier.  

After completing the order, the supplier sends a confirmation to the squad.  

There is also the administration module. The administrator gets a copy of all messages sent on the system
and can send messages to all suppliers, all squads or all system users.

There is a [diagram](src/xenoteo/com/github/homework/diagram.pdf) for the solution provided.

### Execution
After installing all the [dependencies](README.md#dependencies), you can run 
[squad](src/xenoteo/com/github/homework/Squad.java), [supplier](src/xenoteo/com/github/homework/Supplier.java) and 
[admin](src/xenoteo/com/github/homework/Admin.java) straightaway.  
If you use IntelliJ and there are dependencies that are not seen go to `File` -> `Invalidate Caches / Restart...`.

## [Lab](https://github.com/xenoteo/Distributed-Systems/tree/main/RabbitMQ/src/xenoteo/com/github/lab)
Basic concepts about Message Oriented Middleware in the example of RabbitMQ platform.