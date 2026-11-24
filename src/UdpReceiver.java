import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Class extending Runnable.
 * Class receives and reads packets in parallel thread.
 * Packet is "unpacked" and the data is transformed
 * back from byte, via String and to a Point object.
 * Then the point object is sent to the paper to be added to the drawing.
 */

public class UdpReceiver implements Runnable {

    private DatagramSocket socket;
    private Paper paper;

    /**
     * Constructor
     * @param port = depending on order of creation.
     * @param paper = Paper.
     * @throws SocketException = exception
     */
    public UdpReceiver(int port, Paper paper) throws SocketException {
        socket = new DatagramSocket(port);
        this.paper = paper;
    }


    /**
     * Continuously listening for incoming packet.
     * When incoming packet, byte data is converted
     * to point object via String.
     * Send point object to Paper to be painted.
     */
    @Override
    public void run() {

        while (true) {
            try {
                byte[] bytes = new byte[1024];

                // Create packet for receiving data.
                DatagramPacket inPacket = new DatagramPacket(bytes, bytes.length);

                // Receive incoming packet.
                socket.receive(inPacket);

                // Transform byte data to String.
                String point = new String(inPacket.getData(), 0, inPacket.getLength());
                String[] xy = point.split(" ");
                // Create Point object from data.
                Point p = new Point(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));

                paper.addPoint(p);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


}














