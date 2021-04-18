import socket

print('PYTHON UDP CLIENT')

serverIP = "127.0.0.1"
serverPort = 9008
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

while True:
    msg = input("Enter the message (q to stop): ")
    if msg == "q":
        client.sendto(bytes("Finishing the transmission... [Ping Python]", 'UTF-8'), (serverIP, serverPort))
        break
    client.sendto(bytes(msg + " [Ping Python]", 'UTF-8'), (serverIP, serverPort))
    buff, address = client.recvfrom(1024)
    print("Received response: " + str(buff, 'UTF-8'))

client.close()
