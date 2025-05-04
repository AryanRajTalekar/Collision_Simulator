import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CollisionSimulator extends JPanel implements ActionListener {
    private Timer timer;
    private double mass1 = 5;
    private double mass2 = 10;
    private double vel1 = 3;
    private double vel2 = -2;
    private int x1 = 100, x2 = 400;
    private int size1, size2;

    private final int HEIGHT = 100;

    public CollisionSimulator(double m1, double m2) {
        this.mass1 = m1;
        this.mass2 = m2;
        this.size1 = (int)(Math.sqrt(mass1) * 10);
        this.size2 = (int)(Math.sqrt(mass2) * 10);
        this.setPreferredSize(new Dimension(800, 300));
        timer = new Timer(20, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw velocity info
        g.setColor(Color.BLACK);
        double v1_mps = vel1 * 0.5;
        double v2_mps = vel2 * 0.5;
        g.drawString(String.format("Velocity1: %.2f m/s", v1_mps), 20, 20);
        g.drawString(String.format("Velocity2: %.2f m/s", v2_mps), 20, 40);


        // Draw rectangles
        g.setColor(Color.RED);
        g.fillRect(x1, HEIGHT, size1, size1);
        g.setColor(Color.BLUE);
        g.fillRect(x2, HEIGHT, size2, size2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        x1 += vel1;
        x2 += vel2;

        // Collision between objects
        if (x1 + size1 >= x2 && x1 <= x2 + size2) {
            double newVel1 = ((mass1 - mass2) * vel1 + 2 * mass2 * vel2) / (mass1 + mass2);
            double newVel2 = ((mass2 - mass1) * vel2 + 2 * mass1 * vel1) / (mass1 + mass2);
            vel1 = newVel1;
            vel2 = newVel2;
        }

        // Wall collisions (left and right)
        if (x1 <= 0 || x1 + size1 >= getWidth()) vel1 = -vel1;
        if (x2 <= 0 || x2 + size2 >= getWidth()) vel2 = -vel2;

        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            double m1 = Double.parseDouble(JOptionPane.showInputDialog("Enter mass of Object 1 (e.g., 5):"));
            double m2 = Double.parseDouble(JOptionPane.showInputDialog("Enter mass of Object 2 (e.g., 10):"));

            JFrame frame = new JFrame("Collision Simulator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new CollisionSimulator(m1, m2));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
