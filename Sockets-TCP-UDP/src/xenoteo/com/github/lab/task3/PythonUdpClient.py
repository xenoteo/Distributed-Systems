import socket

print('PYTHON UDP CLIENT')

serverIP = "127.0.0.1"
serverPort = 9008
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

while True:
    msg = input("Enter the message (q to stop): ")
    if msg == "q":
        break
    msg_bytes = int(msg).to_bytes(4, byteorder='little')
    client.sendto(msg_bytes, (serverIP, serverPort))

    buff, address = client.recvfrom(1024)
    response = int.from_bytes(buff, byteorder='little')
    print(f"Received response: {response}")
