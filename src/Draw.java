import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;

/**
 * Program entry point receives two args from user:
 * Listening port & sending port, for example for two terminals. 5000, 5001 & 5001, 5000.
 * When mouse is clicked and dragged, points are saved to HashSet & sent to UdpClient.
 * In UdpClient points are converted into bytes via String.
 * From UdpReceiver Paper receives point objects created in Paper (here).
 * Points are created, sent, converted, sent, converted and received here to be painted.
 */

public class Draw extends JFrame {

    private Paper p;

    public static void main(String[] args) {

        // Ports
        int vanEyck = Integer.parseInt(args[0]);
        int derWeyden = Integer.parseInt(args[1]);

        new Draw(vanEyck, derWeyden);
    }

    public Draw(int vanEyck, int derWeyden) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        p = new Paper(derWeyden);

        getContentPane().add(p, BorderLayout.CENTER);

        setSize(640, 480);
        setVisible(true);

        // Create receiver with port and Paper.
        // UdpReceiver continuously listen for incoming packets from UdpClient.
        try {
            UdpReceiver receiver = new UdpReceiver(vanEyck, p);

            // Start thread to be able to continuously listen for incoming packets.
            Thread thread = new Thread(receiver);
            thread.start();

        } catch (SocketException e) {
            e.printStackTrace();
        }


    }
}


class Paper extends JPanel {
    private UdpClient udpClient;

    private HashSet<Point> points = new HashSet<>();

    public Paper(int derWeyden) {
        setBackground(Color.white);
        addMouseListener(new L1());
        addMouseMotionListener(new L2());

        // Create UdpClient with host and port.
        // UdpClient takes Point data, converts it and sends packet with socket.
        try {
            udpClient = new UdpClient("127.0.0.1", derWeyden);
        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        for(Point p : points) {
            g.fillOval(p.x, p.y, 8, 8);
        }
    }

    // adds point to HashSet
    public void addPoint(Point p) {
        points.add(p);
        repaint();

        // Send painted point to UdpCLient.
        // In UdpCLient, convert to bytes and send packet with socket.
        try {
            udpClient.sendPoint(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class L1 extends MouseAdapter {
        public void mousePressed(MouseEvent me) {
            addPoint(me.getPoint());
        }
    }

    class L2 extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent me) {
            addPoint(me.getPoint());
        }
    }

}