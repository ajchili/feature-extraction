import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class EdgeDetection {

    private static BufferedImage image;
    private static int[][] kernel = new int[][] {
            { 1, 0, -1, 0, 0, 0, -1, 0, 1 },
            { 0, 1, 0, 1, -4, 1, 0, 1, 0 },
            { -1, -1, -1, -1, 8, -1, -1, -1, -1} };
    private static int[][] kernels = new int[][] {
            { -1, 0, 1, -2, 0, 2, -1, 0, 1 },
            { 1, 2, 1, 0, 0, 0, -1, -2, -1 } };

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("File and kernel type must be provided!");
            return;
        }

        File file = new File(args[0]);
        try {
            image = convertImage(ImageIO.read(file));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("No image file provided.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage imageEdges = new BufferedImage(image.getWidth(), image.getHeight(), TYPE_INT_RGB);
        Graphics g = imageEdges.getGraphics();
        try {
            if (args[1].equals("true")) {
                for (int i = 0; i < image.getWidth(); i++) {
                    for (int j = 0; j < image.getHeight(); j++) {
                        int c = convolution(new int[] {i, j}, kernels[0]);
                        g.setColor(new Color(c, c, c));
                        g.drawRect(i, j, 1, 1);
                    }
                }
                for (int i = 0; i < image.getWidth(); i++) {
                    for (int j = 0; j < image.getHeight(); j++) {
                        int c = convolution(new int[] {i, j}, kernels[1]);
                        g.setColor(new Color(c, c, c));
                        g.drawRect(i, j, 1, 1);
                    }
                }
            } else {
                for (int i = 0; i < image.getWidth(); i++) {
                    for (int j = 0; j < image.getHeight(); j++) {
                        {
                            int c = convolution(new int[]{i, j}, kernel[Integer.parseInt(args[1])]);
                            g.setColor(new Color(c, c, c));
                            g.drawRect(i, j, 1, 1);
                        }
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid kernel type selected, " +
                    "either provide true for second argument or select a kernel (0-2).");
        }
        g.dispose();
        try {
            String fileExtension = file.getName().substring(file.getName().lastIndexOf("."));
            ImageIO.write(imageEdges, fileExtension, new File("edges" + fileExtension));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage convertImage(BufferedImage image) {
        BufferedImage convertedImage = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = convertedImage.getGraphics();
        g.drawImage(image,0, 0, null);
        g.dispose();
        return convertedImage;
    }

    private static int convolution(int[] position, int[] kernel) {
        int[] matrix = flipMatrix(kernel);
        int value = 0;
        if (position[0] - 1 > 0
                && position[0] + 1 < image.getWidth()
                && position[1] - 1 > 0
                && position[1] + 1 < image.getHeight()) {
            int iteration = 0;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    value += new Color(
                            image.getRGB(position[0] + i, position[1] + j)).getRed() * matrix[iteration];
                    iteration++;
                }
            }
        }

        return value > 0 ? value <= 255 ? value : 255 : 0;
    }

    private static int[] flipMatrix(int[] matrix) {
        int length = matrix.length;
        int[] flippedMatrix = new int[length];
        for (int i = 0; i < length; i++) {
            flippedMatrix[length - 1 - i] = matrix[i];
        }
        return flippedMatrix;
    }
}