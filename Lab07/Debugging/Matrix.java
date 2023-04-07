import java.util.Scanner;

public class Matrix {

    /*
     * Number of rows in this matrix
     */
    private final int rows;
    /*
     * Number of columns in this matrix
     */
    private final int cols;
    /*
     * A 2D array storing the elements of the matrix
     */
    private final int[][] matrix;

    /*
     * A constructor to create an empty matrix given the size of the matrix and receive the
     * elements through user input.
     */
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = new int[rows][cols];
    }

    /*
     * Creates a Matrix object given a 2D integer array
     */
    public Matrix(int[][] matrix) {
        this.rows = matrix.length;
        this.cols = matrix[0].length;
        this.matrix = matrix;
    }

    /*
     * Prints the elements of the matrix
     */
    public void printMatrix() {
        String string = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                string += matrix[i][j] + " ";
            }
            string += "\n";
        }
        System.out.print(string);
    }

    //Getters and Setters
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getElement(int rowIndex, int colIndex) {
        return matrix[rowIndex][colIndex];
    }

    public void setElement(int rowIndex, int colIndex, int val) {
        matrix[rowIndex][colIndex] = val;
    }

    /**
     * Returns a matrix with the sign of all numbers in current matrix flipped
     * <p>
     * If we have a Matrix with elements :
     * 1 2 3
     * 4 5 6
     * 7 8 9
     * <p>
     * The following should be returned:
     * -1 -2 -3
     * -4 -5 -6
     * -7 -8 -9
     */
    public Matrix additiveInverse() {
        Matrix inverse = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                inverse.setElement(i, j, -1 * matrix[i][j]);
            }
        }
        return inverse;
    }

    /**
     * Returns a matrix representing the sum of the current matrix and a provided matrix
     * If we provide the matrices:
     * 1 2      5 6
     * 2 1      6 5
     * The following should be returned:
     * 6 8
     * 8 6
     * <p>
     * Note that matrices have to be of the same dimensions to be added. If they aren't, return null.
     */
    public Matrix addMatrices(Matrix matrix) {
        if (this.getRows() != matrix.getRows() || this.getCols() != matrix.getCols()) {
            return null;
        }
        Matrix sumMatrix = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sumMatrix.setElement(i, j, this.getElement(i, j) + matrix.getElement(i, j));
            }
        }
        return sumMatrix;
    }

    /**
     * Returns a matrix representing the difference of the current matrix and a provided matrix
     * If we provide the matrices:
     * 1 6      5 2
     * 6 1      2 5
     * The following should be returned:
     * -4  4
     * 4 -4
     * <p>
     * Note that matrices have to be of the same dimensions to be subtracted. If they aren't, return null.
     */
    public Matrix subtractMatrices(Matrix matrix) {
        return addMatrices(matrix.additiveInverse());
    }

    public static void main(String[] args) {
        int rows;
        int cols;
        Matrix[] matrices = new Matrix[2];
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number of rows:");
        rows = sc.nextInt();
        System.out.println("Enter number of columns:");
        cols = sc.nextInt();

        for (int i = 0; i < 2; i++) {
            System.out.println("Matrix No. " + (i + 1));
            System.out.println("Enter integers to be added to matrix");
            matrices[i] = new Matrix(rows, cols);
            for (int j = 0; j < rows; j++) {
                for (int k = 0; k < cols; k++) {
                    matrices[i].setElement(j, k, sc.nextInt());
                }
            }
            System.out.println("Matrix " + (i + 1) + ":");
            matrices[i].printMatrix();
        }
        Matrix sum = matrices[0].addMatrices(matrices[1]);
        Matrix diff = matrices[0].subtractMatrices(matrices[1]);
        System.out.println("Sum: ");
        sum.printMatrix();
        System.out.println("Difference: ");
        diff.printMatrix();
    }
}