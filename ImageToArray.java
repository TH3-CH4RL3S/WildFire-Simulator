import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageToArray {

    public static Cell[][] getInitArray(int size, String inputFile) {
        try {
            // Load the output image
            //BufferedImage image = ImageIO.read(new File("resized_image14.png"));
            BufferedImage image = ImageIO.read(new File(inputFile));

            // Get the dimensions of the image
            int width = image.getWidth();
            int height = image.getHeight();

            // Define threshold values
            //final int GREEN_THRESHOLD = 100;
            
            final int BLUE_THRESHOLD = 100;
            final int GRAY_THRESHOLD = 50;

            // Create a 2D array to store cell information
            Cell[][] cellArray = new Cell[height][width];

            // Iterate over the image and store cell information
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Color pixelColor = new Color(image.getRGB(x, y));
                    cellArray[y][x] = new Cell();

                    int red = pixelColor.getRed();
                    int green = pixelColor.getGreen();
                    int blue = pixelColor.getBlue();

                    /*
                    if (isGreen(red, green, blue, GREEN_THRESHOLD)) {
                        cellArray[y][x].setState(CellState.FOREST); // Green
                        //System.out.println("Green");
                    }*/ 
                    
                    if (isGray(red, green, blue, GRAY_THRESHOLD)){
                        cellArray[y][x].setState(CellState.ROCK); // Gray
                        //System.out.println("Gray"); 
                    } else if (isBlue(red, green, blue, BLUE_THRESHOLD)) {
                        cellArray[y][x].setState(CellState.WATER); // Blue
                        //System.out.println("Blue");
                    } else {
                        cellArray[y][x].setState(CellState.FOREST); //Green
                    }
                }
            }

            /* Now cellArray contains the information of each cell in the image
            // Each element is an integer representing the cell type (0 for gray, 1 for green, 2 for blue)

            // Example: Print the information of the cell at position (0, 0)
            int cellType = cellArray[100][1000];
            System.out.println("Cell type at (0, 0): " + cellType);
            */
            return cellArray;

        } catch (IOException e) {
            e.printStackTrace();
            Cell[][] error = new Cell[1][1];
            error[0][0].setState(CellState.BURNT);
            return error;
        }
    }

    /*
    // Method to determine if a color is green based on threshold values
    private static boolean isGreen(int red, int green, int blue, int greenThreshold) {
        return green >= greenThreshold && green > red && green > blue;
    }

    */

    // Method to determine if a color is blue based on threshold values
    private static boolean isBlue(int red, int green, int blue, int blueThreshold) {
        return blue >= blueThreshold && blue > red && blue > green;
    }

    // Method to determine if a color is gray based on threshold values
    private static boolean isGray(int red, int green, int blue, int grayThreshold) {
        return Math.abs(red - green) < grayThreshold &&
                Math.abs(red - blue) < grayThreshold &&
                Math.abs(green - blue) < grayThreshold;
    }
}
