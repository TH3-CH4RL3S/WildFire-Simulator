import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageResizer {

    public static void resizeImage(String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight) {
        try {
            // Load the input image
            BufferedImage inputImage = ImageIO.read(new File(inputImagePath));

            // Create a new image with the specified dimensions
            BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());

            // Iterate over the new image and resize each pixel
            for (int y = 0; y < scaledHeight; y++) {
                for (int x = 0; x < scaledWidth; x++) {
                    // Calculate the corresponding pixel coordinates in the original image
                    int sourceX = x * inputImage.getWidth() / scaledWidth;
                    int sourceY = y * inputImage.getHeight() / scaledHeight;

                    // Get the color of the corresponding pixel in the original image
                    Color color = new Color(inputImage.getRGB(sourceX, sourceY));

                    // Check if the pixel is green, blue, or gray
                    if (isGray(color)) {
                        outputImage.setRGB(x, y, Color.GRAY.getRGB());
                    } else if (isBlue(color)) {
                        outputImage.setRGB(x, y, Color.BLUE.getRGB());
                    } else {
                        outputImage.setRGB(x, y, Color.GREEN.getRGB());
                    }
                }
            }

            // Save the resized image to the specified output file
            ImageIO.write(outputImage, "png", new File(outputImagePath));

            System.out.println("Resized image saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to check if a color is gray
    private static boolean isGray(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int grayThreshold = 30; // Adjust as needed
        return Math.abs(red - green) < grayThreshold &&
               Math.abs(red - blue) < grayThreshold &&
               Math.abs(green - blue) < grayThreshold;
    }

    // Method to check if a color is blue
    private static boolean isBlue(Color color) {
        int blueThreshold = 50; // Adjust as needed
        return color.getBlue() > blueThreshold && color.getBlue() > color.getRed() && color.getBlue() > color.getGreen();
    }

    public static void Converter(int size, String input, String output) {
        /* 
        String inputImagePath = "image5.png";
        String outputImagePath = "resized_image14.png"; 
        */

        String inputImagePath = input;
        String outputImagePath = output;
        int scaledWidth = size; // Size of the model
        int scaledHeight = size; 
        resizeImage(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
    }
}
