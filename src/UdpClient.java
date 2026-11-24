import java.awt.*;
import java.net.*;

/**
 * Class takes Point sent from Paper.
 * Converts point to bytes via String.
 * Creates a packet to send by socket.
 */
public class UdpClient {

    private DatagramSocket socket;
    private InetAddress host;
    private int port;

    public UdpReceiver udpReceiver;

    /**
     * Constructor
     * @param address = host
     * @param port = depending on order of creation.
     * @throws SocketException = exception
     * @throws UnknownHostException = exception
     */
    public UdpClient(String address, int port) throws SocketException, UnknownHostException {
        socket = new  DatagramSocket();
        host = InetAddress.getByName(address);
        this.port = port;
    }

    // Used in Paper to send point
    public void sendPoint(Point p) throws Exception {
        // convert point to String:
        String message = Integer.toString(p.x) + " " + Integer.toString(p.y);

        // convert String to byte array:
        byte[] data = message.getBytes();

        // Outgoing packet containing byte data, data size, ip/address, port.
        DatagramPacket outPacket = new DatagramPacket(data, data.length, host, port);
        socket.send(outPacket);
    }



}
