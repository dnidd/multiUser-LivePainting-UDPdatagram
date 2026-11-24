### Datagram sockets with unicasts
Multi users live painting program implementing datagram
socket using UDP.

The data is managed in DatagramPacket packets.
For sending: data, size, host, port.
For receiving: data, data size. 

Each user opens an application/panel. Everything that is drawn
is sent by UDP to another user/identical program that is live
rendering the exact same drawing. Two programs are live at
the same time simulating two users. 

The two running programs needs both a listening port & a sending port, 
for example for two terminals. 5000, 5001 & 5001, 5000.

compile in root:<br>
javac $(find src -name "*.java")

Execute terminal 1: java -cp "src/Draw:src" Draw 5000 5001
Execute terminal 2: java -cp "src/Draw:src" Draw 5001 5000


Literature:
Java Network Programming 4th edition (Elliotte Rusty Harold) s.393

YouTube tutorials:
https://www.youtube.com/watch?v=yxIUYQDtWpc&t=86s<br>
https://www.youtube.com/watch?v=7tznk2SWCZs&t=420s