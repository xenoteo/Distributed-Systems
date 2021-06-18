# gRPC
Homework

## Task description
*You can also read the task description in [Polish](HOMEWORK_POLISH.md).*  

The aim of the task is to build a client-server application for ordering official matters (issues) and obtaining 
information about their outcome. The time for the consideration of the case by the office is roughly determined 
at the time of its submission and may be around an hour or two (for the purposes of the implementation and 
demonstration of the task, it should be assumed that it is shorter). The ordering party (client) is interested in 
finding out about the result of the reported case as soon as possible, but being late a minute or two will not 
be a problem. It cannot be assumed that every client will want to have the application running for the entire 
duration of the case.  

The Office handles many types of cases (differing in the set of information sent or returned), it can be assumed 
that all types of cases are known at the time of creating the system (for the needs of the task it is enough to 
provide three examples).

The priority in the implementation of the task is the selection, design and implementation of the appropriate method 
of communication - minimizing the number of unnecessary calls. All elegant options for implementing communication 
are permissible.

## Solution description
Client is written in Pythona nd server is written in Java. Clients hold some information in a local database. 
Clients are identified by their IDs. In the database clients keep information whether they collected the last issue 
or not as well as a type of the last issue requested. Clients cannot send new requests before they get response to 
a previous one.  
  
**Warning**: this is not an optimal solution, as when client dies not received a response from the server,
then after his rebirth the duplicate of the request is sent, so that client waits twice more.

## Requirements
To get more detailed information about requirements go to
[gRPC documentation page](https://grpc.io/docs/languages/python/quickstart/).
### Prerequisites
- Python 3.5 or higher
- `pip` version 9.0.1 or higher

### gRPC
`python -m pip install grpcio`

### gRPC tools
`python -m pip install grpcio-tools`

## Execution
The main files to run are [MunicipalOfficeServer.java](src/main/java/xenoteo/com/github/server/MunicipalOfficeServer.java)
and [municipal_office_client.py](src/main/java/xenoteo/com/github/client/municipal_office_client.py).

If you use Intellij, right click on folder `gen` ->
`Mark Directory as` -> `Generated Sources Root`.  
  
To use [Python in Intellij](https://www.jetbrains.com/help/idea/plugin-overview.html#90e61d91),
configure [Python SDK](https://www.jetbrains.com/help/idea/configuring-python-sdk.html) 
(e.g. [a system interpreter](https://www.jetbrains.com/help/idea/configuring-local-python-interpreters.html)). You can
also run Python client from the console.
  
Make sure all jars from the `lib` folder are attached to your project.  
  
To get access to the database console go to `View | Tool Windows | Database` ->
add new `Data Source` -> `SQLite` and provide `File` path `src/main/java/xenoteo/com/github/client/mo.db`. 
After that, you will be able to use database console.
  
If you use IntelliJ and there are dependencies that are not seen go to `File` -> `Invalidate Caches / Restart...`.  

## Code generation
### Java
To generate Java code based on [proto](src/main/resources/municipal_office.proto) file, use maven 
(run the following command from project root folder):  
`mvn compile`

### Python
To generate Python code based on [proto](src/main/resources/municipal_office.proto) file, 
run the following command from project root folder:  
`python -m grpc_tools.protoc --proto_path=src/main/resources -I=src/main/java/xenoteo/com/github/client 
--python_out=src/main/java/xenoteo/com/github/client --grpc_python_out=src/main/java/xenoteo/com/github/client 
municipal_office.proto`

## Helpful resources
- https://grpc.io/
- https://grpc.io/docs/languages/java/quickstart/
- https://grpc.io/docs/languages/python/quickstart/
