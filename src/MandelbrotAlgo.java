import parcs.*;
//
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MandelbrotAlgo implements AM{
    public void run(AMInfo info)
    {
        System.out.println("Start executing");
        long startTime = System.nanoTime();

        double xc = info.parent.readDouble();
        double yc = info.parent.readDouble();
        double zoom = info.parent.readDouble();
        int maxIter = info.parent.readInt();
        int imgSize = info.parent.readInt();
        int xChunk = info.parent.readInt();
        int yChunk = info.parent.readInt();
        int chunkSize = info.parent.readInt();

        var resultImg = GetMandelbrotChunk(xc, yc, zoom, maxIter, imgSize, xChunk, yChunk, chunkSize);
        //var resultImg = new BufferedImage(chunkSize, chunkSize, BufferedImage.TYPE_INT_RGB);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
			ImageIO.write(resultImg, "jpg", baos);
		} catch (IOException e) {System.out.println("Exception");}
        

        info.parent.write(xChunk);
        info.parent.write(yChunk);
        
        //var imgBytes = baos.toByteArray();
        //info.parent.write(imgBytes.length);
        //info.parent.write(imgBytes);
        
        double estimatedTime = (double) (System.nanoTime() - startTime) / 1000000000;
        System.out.println("Time total (excluding IO): " + estimatedTime);
    }
//
    public static BufferedImage GetMandelbrotChunk(double xc, double yc, double zoom, int maxIter, int imgSize, int xChunk, int yChunk, int chunkSize)
    {
        BufferedImage bufferedImage = new BufferedImage(chunkSize, chunkSize, BufferedImage.TYPE_INT_RGB);
        double minX = xc - zoom / 2;
        double minY = yc - zoom / 2;

        Complex c0 = new Complex(0, 0);
        Complex z0 = new Complex(0, 0);

        for (int x = 0; x < chunkSize; x++) {
            for (int y = 0; y < chunkSize; y++) {
                c0.R = minX + ((xChunk + x) / (double)imgSize) * zoom;
                c0.I = minY + ((yChunk + y) / (double)imgSize) * zoom;
                z0.R = 0;
                z0.I = 0;

                int iterations = 0;
                do {
                    iterations++;
                    z0.Square();
                    z0.Add(c0);
                    if(z0.Magnitude() > 2.0) break;
                }while (iterations < maxIter);

                var colorFactor = Math.abs(1.0 - (double) iterations / (double) maxIter);
                int gray = (int)(colorFactor * 255);
                //var colorColor = Color.getHSBColor((float) (colorFactor * 359) , 100, (int)(colorFactor * 100));
                Color color = new Color(gray, gray, gray);

                bufferedImage.setRGB(x, chunkSize - 1 - y, color.getRGB());
            }
        }
        return bufferedImage;
    }

}
