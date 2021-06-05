# Resources
- https://grpc.io/
- https://grpc.io/docs/languages/java/basics/
- https://github.com/techtter/grpc
- https://github.com/19mariusz95/Rozprochy/tree/master/Lab4

# Python code generation
`
python -m grpc_tools.protoc --proto_path=src/main/resources -I=src/main/java/xenoteo/com/github/client --python_out=src/main/java/xenoteo/com/github/client --grpc_python_out=src/main/java/xenoteo/com/github/client municipal_office.proto
`