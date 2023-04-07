import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class VisualSort {
    // This inner class represents the clock that keeps track of the sorting time
    private static class Clock {
        private long startTime;
        private long endTime;

        // This method starts the clock
        public void start() {
            startTime = System.currentTimeMillis();
        }

        // This method stops the clock
        public void stop() {
            endTime = System.currentTimeMillis();
        }

        // This method returns the elapsed time in milliseconds
        public long getElapsedTime() {
            return endTime - startTime;
        }

        // This method updates the clock
        public void update() {
            endTime = System.currentTimeMillis();
        }
    }

    // This inner class represents the panel that displays the sorting process
    private static class SortPanel extends JPanel {
        private int[] array;
        private int width;
        private int height;
        private int currentIndex;

        // This constructor initializes the panel with the given array and size
        public SortPanel(int[] array, int width, int height) {
            this.array = array;
            this.width = width;
            this.height = height;
            setPreferredSize(new Dimension(width, height));
            setBackground(Color.BLACK);
        }

        // This method paints the panel with the given graphics context
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Calculate the bar width and gap size
            int barWidth = width / array.length;
            int gap = barWidth / 10;
            // Draw the bars
            for (int i = 0; i < array.length; i++) {
                int x = i * barWidth + gap;
                int y = height - array[i];
                int w = barWidth - 2 * gap;
                int h = array[i];
                // Color the current rectangle being sorted red
                if (i == currentIndex) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(x, y, w, h);
            }
        }

// This method sets the index
// This method updates the current index
public void setCurrentIndex(int index) {
    currentIndex = index;
}
    }

    // This class represents the frame that contains the clock and the panel
    private static class SortFrame extends JFrame {
        private Clock clock;
        private SortPanel sortPanel;

        // This constructor initializes the frame with the given array and size
        public SortFrame(int[] array, int width, int height) {
            clock = new Clock();
            sortPanel = new SortPanel(array, width, height);
            setTitle("Visual Sort");
            setSize(width, height + 40);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);
            add(sortPanel);
        }

        // This method updates the clock and the panel
        public void update() {
            clock.update();
            sortPanel.repaint();
        }

        // This method sets the current index
        public void setCurrentIndex(int index) {
            sortPanel.setCurrentIndex(index);
        }
    }

    // This class represents the sorting algorithm
    private static class Sorter {
        private int[] array;
        private SortFrame sortFrame;
        private Clock clock;

        // This constructor initializes the sorter with the given array and frame
        public Sorter(int[] array, SortFrame sortFrame) {
            this.array = array;
            this.sortFrame = sortFrame;
            this.clock = new Clock();
        }

        // This method sorts the given array using the given sorting algorithm
        public void sort(String algorithm) {
            // Start the clock
            clock.start();
            if (algorithm.equalsIgnoreCase("bubble")) {
                // Perform bubble sort
                for (int i = 0; i < array.length - 1; i++) {
                    for (int j = 0; j < array.length - i - 1; j++) {
                        if (array[j] > array[j + 1]) {
                            int temp = array[j];
                            array[j] = array[j + 1];
                            array[j + 1] = temp;
                            // Update the current index
                            sortFrame.setCurrentIndex(j);
                            sortFrame.update();
                        }
                    }
                }
            } else if (algorithm.equalsIgnoreCase("selection")) {
                // Perform selection sort
                for (int i = 0; i < array.length - 1; i++) {
                    int minIndex = i;
                    for (int j = i + 1; j < array.length; j++) {
                        if (array[j] < array[minIndex]) {
                            minIndex = j;
                        }
                    }
                    if (minIndex != i) {
                        int temp = array[i];
                        array[i] = array[minIndex];
                        array[minIndex] = temp;
                        // Update the current index
                        sortFrame.setCurrentIndex(i);
                        sortFrame.update();
                    }
                }
            } else if (algorithm.equalsIgnoreCase("insertion")) {
                // Perform insertion sort
                for (int i = 1; i < array.length; i++) {
                    int key = array[i];
                    int j = i - 1;
                    while (j >= 0 && array[j] > key) {
                        array[j + 1] = array[j];
                        j--;
                        // Update the current index
                        sortFrame.setCurrentIndex(j + 1);
                        sortFrame.update();
                    }
                    array[j + 1] = key;
                }
            }
            // Stop the clock
            clock.stop();
        }
    }

    public static void main(String[] args) {
        // Prompt the user for the size of the array of numbers
        String input = JOptionPane.showInputDialog("Enter the size of the array:");
        int size = Integer.parseInt(input);
        // Prompt the user for the sorting algorithm
        input = JOptionPane.showInputDialog("Enter the sorting algorithm (bubble, selection, or insertion):");
        String algorithm = input;
        // Create the array of random numbers
        int[] array = new int[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int) (Math.random() * array.length);
        }
        // Get the screen size
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        // Create the frame and the sorter
        SortFrame sortFrame = new SortFrame(array, width, height);
        Sorter sorter = new Sorter(array, sortFrame);
        // Sort the array
        sorter.sort(algorithm);
        // Display the elapsed time
        JOptionPane.showMessageDialog(null, "Elapsed time: " + sorter.clock.getElapsedTime() + " milliseconds");
        // Terminate the program
        System.exit(0);
    }
}
