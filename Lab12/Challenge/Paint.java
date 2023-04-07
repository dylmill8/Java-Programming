import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * This class creates a drawing program with options to change the hex or rgb color codes of the drawing pen, clear the
 * drawing, switch to an eraser, or randomize their drawing tool color
 *
 * @author Dylan
 * @version November 15, 2022
 */

public class Paint extends JComponent implements Runnable {
    Random random = new Random();
    Color backgroundColor = Color.white;
    private Image image; // the canvas
    private Graphics2D graphics2D;  // this will enable drawing
    private int curX; // current mouse x coordinate
    private int curY; // current mouse y coordinate
    private int oldX; // previous mouse x coordinate
    private int oldY; // previous mouse y coordinate

    JButton clrButton;
    JButton fillButton;
    JButton eraseButton;
    JButton randomButton;

    JButton hexButton;
    JButton rgbButton;

    JTextField hexText;
    JTextField rText;
    JTextField gText;
    JTextField bText;

    Paint paint; // variable of the type ColorPicker

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Paint());
    }

    public void run() {
        /* set up JFrame */
        JFrame frame = new JFrame("Paint");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        paint = new Paint();
        content.add(paint, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        clrButton = new JButton("Clear");
        panel.add(clrButton);
        content.add(panel, BorderLayout.NORTH);
        clrButton.addActionListener(actionListener);

        fillButton = new JButton("Fill");
        panel.add(fillButton);
        content.add(panel, BorderLayout.NORTH);
        fillButton.addActionListener(actionListener);

        eraseButton = new JButton("Erase");
        panel.add(eraseButton);
        content.add(panel, BorderLayout.NORTH);
        eraseButton.addActionListener(actionListener);

        randomButton = new JButton("Random");
        panel.add(randomButton);
        content.add(panel, BorderLayout.NORTH);
        randomButton.addActionListener(actionListener);

        panel = new JPanel();
        hexText = new JTextField("#", 10);
        panel.add(hexText);
        content.add(panel, BorderLayout.SOUTH);
        hexText.addActionListener(actionListener);

        hexButton = new JButton("Hex");
        panel.add(hexButton);
        content.add(panel, BorderLayout.SOUTH);
        hexButton.addActionListener(actionListener);

        rText = new JTextField("", 5);
        panel.add(rText);
        content.add(panel, BorderLayout.SOUTH);
        rText.addActionListener(actionListener);

        gText = new JTextField("", 5);
        panel.add(gText);
        content.add(panel, BorderLayout.SOUTH);
        gText.addActionListener(actionListener);

        bText = new JTextField("", 5);
        panel.add(bText);
        content.add(panel, BorderLayout.SOUTH);
        bText.addActionListener(actionListener);

        rgbButton = new JButton("RGB");
        panel.add(rgbButton);
        content.add(panel, BorderLayout.SOUTH);
        rgbButton.addActionListener(actionListener);

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    protected void paintComponent(Graphics g) {
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            /* this lets us draw on the image (ie. the canvas)*/
            graphics2D = (Graphics2D) image.getGraphics();
            /* gives us better rendering quality for the drawing lines */
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            /* set canvas to white with default paint color */
            graphics2D.setPaint(Color.white);
            graphics2D.fillRect(0, 0, getSize().width, getSize().height);
            graphics2D.setPaint(Color.black);
            repaint();
        }
        g.drawImage(image, 0, 0, null);

    }

    /* action listener for buttons */
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == clrButton) {
                hexText.setText("#");
                rText.setText("");
                gText.setText("");
                bText.setText("");
                paint.clrButton();
            }
            if (e.getSource() == fillButton) {
                hexText.setText("#");
                rText.setText("");
                gText.setText("");
                bText.setText("");
                paint.fillButton();
            }
            if (e.getSource() == eraseButton) {
                paint.eraseButton();
            }
            if (e.getSource() == randomButton) {
                int r = random.nextInt(256);
                int g = random.nextInt(256);
                int b = random.nextInt(256);
                rText.setText(String.valueOf(r));
                gText.setText(String.valueOf(g));
                bText.setText(String.valueOf(b));
                hexText.setText(String.format("#%02x%02x%02x", r, g, b));
                paint.randomButton(r, g, b);
            }

            if (e.getSource() == rgbButton) {
                int r = 0;
                int g = 0;
                int b = 0;
                String colorValue;

                colorValue = rText.getText();
                if (!colorValue.equals("")) {
                    r = Integer.parseInt((colorValue));
                } else { rText.setText("0"); }
                colorValue = gText.getText();
                if (!colorValue.equals("")) {
                    g = Integer.parseInt((colorValue));
                } else { gText.setText("0"); }
                colorValue = bText.getText();
                if (!colorValue.equals("")) {
                    b = Integer.parseInt((colorValue));
                } else { bText.setText("0"); }

                try {
                    Color rgbColor = new Color(r, g, b);
                    paint.rgbButton(rgbColor);
                    hexText.setText(String.format("#%02x%02x%02x", r, g, b));
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(null, "Not a valid RGB Value",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (e.getSource() == hexButton) {
                try {
                    Color hexColor = Color.decode(hexText.getText());
                    paint.hexButton(hexColor);
                    rText.setText(String.valueOf(hexColor.getRed()));
                    gText.setText(String.valueOf(hexColor.getGreen()));
                    bText.setText(String.valueOf(hexColor.getBlue()));
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(null, "Not a valid Hex Value",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        }
    };

    public void clrButton() {
        graphics2D.setPaint(Color.white);
        graphics2D.fillRect(0, 0, getSize().width, getSize().height);
        backgroundColor = Color.white;
        graphics2D.setPaint(Color.black);
        repaint();
    }

    public void fillButton() {
        graphics2D.fillRect(0, 0, 600, 400);
        backgroundColor = (Color) graphics2D.getPaint();
        graphics2D.setPaint(Color.black);
        repaint();
    }

    public void eraseButton() {
        graphics2D.setPaint(backgroundColor);
    }

    public void randomButton(int r, int g, int b) {
        Color rgbColor = new Color(r, g, b);
        graphics2D.setPaint(rgbColor);
    }

    public void rgbButton(Color rgbColor) {
        graphics2D.setPaint(rgbColor);
    }

    public void hexButton(Color hexColor) {
        graphics2D.setPaint(hexColor);
    }

    public Paint() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                /* set oldX and oldY coordinates to beginning mouse press*/
                oldX = e.getX();
                oldY = e.getY();
            }
        });


        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                graphics2D.setStroke(new BasicStroke(5));
                /* set current coordinates to where mouse is being dragged*/
                curX = e.getX();
                curY = e.getY();

                /* draw the line between old coordinates and new ones */
                graphics2D.drawLine(oldX, oldY, curX, curY);

                /* refresh frame and reset old coordinates */
                repaint();
                oldX = curX;
                oldY = curY;

            }
        });
    }
}