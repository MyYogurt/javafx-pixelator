package pixelator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Provides tools for pixalting images
 */
public class Pixelate {

    /**
     * Returns a pixelated version of the image
     * @param inputImage Image to be pixelated
     * @param blockSize The smaller the blocksize, the more pixelated the image
     * @return Pixelated image
     */
    public static BufferedImage pixelate(BufferedImage inputImage, int blockSize) {
        BufferedImage pixelated = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), inputImage.getType());
        int xblocks = inputImage.getWidth() / blockSize;
        int yblocks = inputImage.getHeight() / blockSize;
        ArrayList<Color> rgbAverages = new ArrayList<Color>(xblocks * yblocks);
        for (int x = 0; x < inputImage.getWidth(); x += xblocks) {
            for (int y = 0; y < inputImage.getHeight(); y += yblocks) {
                int counter = 0;
                long sumr = 0;
                long sumg = 0;
                long sumb = 0;
                for (int xcount = x; xcount < x + xblocks; xcount++) {
                    for (int ycount = y; ycount < y + yblocks; ycount++) {
                        Color currentColor = new Color(inputImage.getRGB(xcount, ycount));
                        sumr += currentColor.getRed();
                        sumg += currentColor.getGreen();
                        sumb += currentColor.getBlue();
                        counter++;
                    }
                }
                sumr /= counter;
                sumg /= counter;
                sumb /= counter;
                rgbAverages.add(new Color((int) sumr, (int) sumg, (int) sumb));
            }
        }
        int ycounter = 0;
        for (int x = 0; x < inputImage.getWidth(); x += xblocks) {
            for (int y = 0; y < inputImage.getHeight(); y += yblocks) {
                int rgb = rgbAverages.get(ycounter).getRGB();
                for (int xcount = x; xcount < x + xblocks; xcount++) {
                    for (int ycount = y; ycount < y + yblocks; ycount++) {
                        pixelated.setRGB(xcount, ycount, rgb);
                    }
                }
                ycounter++;
            }
        }
        return pixelated;
    }

    /**
     * Verifies the block size is compatible
     * @param width image width
     * @param height image height
     * @param blockCount blockCount
     * @return If blockCount is valid
     */
    public static boolean verifyBlockCount(int width, int height, int blockCount) {
        return width % blockCount == 0 && height % blockCount == 0;
    }

    /**
     * Returns the file extension of the path
     * For example: "src/foo.txt" returns "txt"
     * @param path path
     * @return file extension
     */
    public static String getFileExtension(String path) {
        return path.substring(path.lastIndexOf('.')+1);
    }
}
