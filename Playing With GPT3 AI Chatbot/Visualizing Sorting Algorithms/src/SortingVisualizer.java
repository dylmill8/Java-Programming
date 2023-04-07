import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

public class SortingVisualizer {

    // This class represents the clock that keeps track of the sorting time
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

        // This method updates the clock
        public void update() {
            endTime = System.currentTimeMillis();
        }

        // This method returns the elapsed time in milliseconds
        public long getElapsedTime() {
            return endTime - startTime;
        }
    }

    // This class represents the panel that displays the sorting process
    private static class SortPanel extends JComponent {
        private int[] array;
        private int width;
        private int height;
        private int currentNumber;

        // This constructor initializes the panel with the given array
        public SortPanel(int[] array, int width, int height) {
            this.array = array;
            this.width = width;
            this.height = height;
            setBackground(Color.BLACK);
            // Initialize the currentNumber to 0
            currentNumber = 0;
        }

        // This method paints the numbers on the panel
        public void paintComponent(Graphics g) {
            // Clear the panel
            g.clearRect(0, 0, getWidth(), getHeight());
            // Compute the width and height of each rectangle
            int barWidth = getWidth() / array.length;
            // Find the maximum number in the array
            int max = Arrays.stream(array).max().getAsInt();
            for (int i = 0; i < array.length; i++) {
                int barHeight = array[i] * getHeight() / max;
                // Highlight the current number being sorted in red
                if (i == currentNumber) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.BLACK);
                }
                // Draw the rectangle using the computed width and height
                g.fillRect(i * barWidth, getHeight() - barHeight, barWidth, barHeight);
            }
        }

        // This method sets the current number being sorted
        public void setCurrentNumber(int currentNumber) {
            this.currentNumber = currentNumber;
            // Repaint the panel to update the highlighting
            repaint();
        }
    }

    // This class represents the frame that contains the clock and the panel
    static class SortFrame extends JFrame {
        private int[] array;
        private Clock clock;
        private SortPanel sortPanel;
        private JButton startButton;
        private boolean isSorting;
        private int currentNumber;

        // This constructor initializes the frame with the given array and size
        public SortFrame(int[] array, int width, int height) {
            this.array = array;
            clock = new Clock();
            sortPanel = new SortPanel(array, width, height);
            setTitle("Visual Sort");
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);
            add(sortPanel);

            // Add the start button
            startButton = new JButton("Start");
            startButton.setBackground(Color.WHITE);
            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    startSorting();
                }
            });
            add(startButton, BorderLayout.SOUTH);

            // Set the isSorting flag to false initially
            isSorting = false;
        }

        // This method starts the sorting process
        public void startSorting() {
            // If the array is already being sorted, do nothing
            if (isSorting) {
                return;
            }
            // Start the clock
            clock.start();
            // Set the isSorting flag to true
            isSorting = true;
            // Start the sorting thread
            Thread thread = new Thread() {
                @Override
                public void run() {
                    // Perform the bubble sort on the array
                    bubbleSort(array);
                    // Stop the clock
                    clock.stop();
                    // Set the isSorting flag to false
                    isSorting = false;
                }
            };
            thread.start();
        }

        // This method performs the bubble sort on the given array
        public void bubbleSort(int[] array) {
            // Repeat until the array is sorted
            while (!isSorted(array)) {
                // Update the currentNumber to 0
                currentNumber = 0;
                // Loop through the array
                for (int i = 0; i < array.length - 1; i++) {
                    // If the current number is greater than the next number, swap them
                    if (array[currentNumber] > array[currentNumber + 1]) {
                        int temp = array[currentNumber];
                        array[currentNumber] = array[currentNumber + 1];
                        array[currentNumber + 1] = temp;
                    }
                    // Update the currentNumber to the next number
                    currentNumber++;
                    // Update the clock
                    clock.update();
                    // Set the current number being sorted in the panel
                    sortPanel.setCurrentNumber(currentNumber);
                    // Sleep for a short time to slow down the sorting process
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        // Do nothing
                    }
                }
            }
        }

        // This method checks if the given array is sorted in ascending order
        public boolean isSorted(int[] array) {
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] > array[i + 1]) {
                    return false;
                }
            }
            return true;
        }
    }
    public static void main(String[] args) {
        // Create a Scanner object to read input from the user
        Scanner scanner = new Scanner(System.in);

        // Read in the size of the array from the user
        System.out.print("Enter the size of the array: ");
        int size = scanner.nextInt();

        // Create the array with the given size
        int[] array = new int[size];

        // Create a Random object to generate random numbers
        Random random = new Random();

        // Create a LinkedHashSet to store the used numbers
        Set<Integer> usedNumbers = new LinkedHashSet<>();

        // Generate random numbers and add them to the array, making sure
        // to only add unique numbers
        for (int i = 0; i < array.length; i++) {
            int number = random.nextInt(size);
            while (usedNumbers.contains(number)) {
                number = random.nextInt(size);
            }
            array[i] = number;
            usedNumbers.add(number);
        }

        // Create the frame with the given array and size
        SortFrame frame = new SortFrame(array, 600, 400);
        frame.setVisible(true);
    }
}