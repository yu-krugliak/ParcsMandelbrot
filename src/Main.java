import parcs.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Start executing");
        long startTime = System.nanoTime();

        task curTask = new task();
        curTask.addJarFile("MandelbrotAlgo.jar");

        var xc = -0.74529;
        var yc = 0.113075;
        var zoom =  1.7E-4;
        var maxIterations = 300;
        var imageSize = 8192;

        AMInfo info = new AMInfo(curTask, null);

        List<channel> channels = new ArrayList<>();
        var columnCount = 4;
        var chunkSize = imageSize / columnCount;

        for(int c = 0; c < columnCount; c++)
        {
            for (int r = 0; r < columnCount; r++)
            {
                point infoPoint = info.createPoint();
                channel pointChannel = infoPoint.createChannel();
                infoPoint.execute("MandelbrotAlgo");
                pointChannel.write(xc);
                pointChannel.write(yc);
                pointChannel.write(zoom);
                pointChannel.write(maxIterations);
                pointChannel.write(imageSize);
                pointChannel.write(c * chunkSize);//Chunk x pos
                pointChannel.write(r * chunkSize);//Chunk y pos
                pointChannel.write(chunkSize);
                channels.add(pointChannel);
            }
        }

        var fractalImage = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
        var graphics = fractalImage.getGraphics();

        for (parcs.channel channel : channels) {
            //System.out.println("\n\n\n\n Processing article" + String.valueOf(i));
            //System.out.println("Waiting for result...");

            var xChunk = channel.readInt();
            var yChunk = channel.readInt();
            var fractalChunk = (BufferedImage) channel.readObject();
            graphics.drawImage(fractalChunk, xChunk, yChunk, null);
        }

        File outputFile = new File("MandelbrotImage.jpg");
        ImageIO.write(fractalImage, "jpg", outputFile);


        double estimatedTime = (double) (System.nanoTime() - startTime) / 1000000000;
        System.out.println("Time total (excluding IO): " + estimatedTime);

        curTask.end();
    }
}
