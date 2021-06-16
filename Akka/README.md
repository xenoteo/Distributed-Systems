# Akka

## [Homework](src/main/java/xenoteo/com/github/homework)
### About
*You can also read the task description in [Polish](HOMEWORK_POLISH.md).*   
  
Monitoring satellite constellations AstraLink:
- constellation counts 100 satellites
- satellites are identified by integers from 100 to 199 inclusively
- satellite status getting is realised by calling a function `getStatus`
from `SatelliteAPI` class (communication with a satellite does not require 
  an active connection)
  
Queries for a satellite status are sent by monitoring stations:
- One query retrieves statuses of `n` consecutive satellites and contains:
    - `query_id` - unique for a monitoring station query ID
    - `first_sat_id` - the ID of the first satellite in a range chosen for monitoring
    - `range` - the number of consecutive satellites which status should be requested
    - `timeout` - the maximum time of waiting for a data from a single satellite
- For example, query `(1, 120, 60, 300)` has a query ID = 1, retrieves statuses of 
satellites 120-179 (inclusively) setting a timeout = 300 ms for each of them.
- A query response contains:
    - `query_id` - a query ID
    - a map `id -> status` for satellites who returned an error, that is a status 
      different from `OK` (we do not send satellites who return `OK`) 
    - information about percent of satellites who have responded within a required timeout
- A response is shown in a console with the following information:
    - a monitoring station name
    - total response time
    - the number of errors retrieved
    - the list of retrieved errors 
- Note: timeout is counted separately for each of satellites (not for the whole request!),
that is if timeout=300ms response from a satellite can be got after 350ms, and after that
  only those satellites are taken into account who responded within 300 ms (and returned an error).
  
Requirements:
- the system in general is to be able to accept queries from various sources, 
  hence all orders are initially sent to one main actor - Dispatcher - which is an 
  access point to the created system
- each monitoring station is a separate actor, but it must send queries to the Dispatcher,
  it cannot communicate directly with the satellites
- when returning the response to the station, it is allowed to omit the Dispatcher, 
  but the station must receive the response in the previously specified format 
  (query_id, map with errors, percentage of responses returned on time)
- When creating, the monitoring station gets a reference to the Dispatcher (for the sake
  of simplicity, we do not use a receptionist)

Non-functional requirements:
- the system is used simultaneously by many monitoring stations, each of which may 
  frequently send inquiries, hence many queries may appear in a short time
- the system is to return answers to the queries in the shortest possible time 
  (avoid solutions that introduce additional delays)
- the system is to operate in a continuous mode (please pay attention to error 
  handling and the lifetime of actors)
- use the attached [configuration file](dispatcher.conf), which will enable the simultaneous activation 
  of the number of actors greater than the default
  
There also should be functionality for saving and reading error statistics using 
a local database:
- the base is to store information about the total number of errors returned by 
  a given satellite (no filtering by error type)
- when starting the system, the base is initialized by entering 0 errors for 
  each satellite (id: 100-199 inclusive)
- after each monitoring order, the database should be updated
- the monitoring station has the option of sending the second type of message - 
  querying the number of errors for a given satellite
  - the query contains the id of the satellite, the response includes the id of the satellite 
    and the number of errors read from the database
  - we do not measure execution time for these messages and no `query_id` is needed
- the monitoring station prints a response to the console ONLY if the number of errors is greater than 0
- it is suggested to use a database working in a single file (e.g. SQLite)
- please make sure that the entry to the database does not slow down the responses to satellite queries

Diagram:  
A [diagram](src/main/java/xenoteo/com/github/homework/StationDispatcherDiagram.pdf) 
should show the actors, sent messages and communication with the database.

### Execution
#### IntelliJ
All dependencies are added via maven. After import of the project to IntelliJ,
it should work normally. If not then go to `File` -> `Invalidate Caches / Restart...`.

#### Maven
You can also run the program using maven runngin the following command:  
`mvn compile exec:java`

## [Lab](src/main/java/xenoteo/com/github/lab)
- [task 0](src/main/java/xenoteo/com/github/lab/task0) - Hello World Akka actor.
- [task 1](src/main/java/xenoteo/com/github/lab/task1) - handling basic math operations 
  (addition, subtraction, multiplication, division) and comparison of error handling 
  strategies (`stop`, `resume`, `restart`).
- [task 2](src/main/java/xenoteo/com/github/lab/task2) - processing text by actors, event subscription.