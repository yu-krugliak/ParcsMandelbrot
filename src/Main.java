import parcs.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Main{
public static void main(String[] argc) {
    	try {
    		task curtask = new task();
    		curtask.addJarFile("MandelbrotAlgo.jar");
    		var info = new AMInfo(curtask, (channel)null);
    		
            System.out.println("Start executing");
            long startTime = System.nanoTime();

            System.out.println("Point 1");

            var xc = -0.74529;
            var yc = 0.113075;
            var zoom =  1.7E-4;
            var maxIterations = 300;
            var imageSize = 512;

            List<channel> channels = new ArrayList<>();
            var columnCount = 4;
            int chunkSize = imageSize / columnCount;
            System.out.println("Point 2");
            /*
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
            }*/
            point infoPoint = info.createPoint();
            channel pointChannel = infoPoint.createChannel();
            infoPoint.execute("MandelbrotAlgo");
            pointChannel.write(xc);
            pointChannel.write(yc);
            pointChannel.write(zoom);
            pointChannel.write(maxIterations);
            pointChannel.write(imageSize);
            pointChannel.write(100);//Chunk x pos
            pointChannel.write(100);//Chunk y pos
            pointChannel.write(chunkSize);
            channels.add(pointChannel);
            
            System.out.println("Point 3");
            var fractalImage = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
            var graphics = fractalImage.getGraphics();
            
            System.out.println("Point 3.1");
            for (parcs.channel channel : channels) {
                //System.out.println("\n\n\n\n Processing article" + String.valueOf(i));
                System.out.println("Waiting for result...");
            	System.out.println("Point 3.2");
            	
            	System.out.println("Point 3.2.1");
                var xChunk = (int)channel.readObject();
                System.out.println("Point 3.2.2");
                var yChunk = (int)channel.readObject();
                System.out.println("Point 3.3");
                //var bytesLength = channel.readInt();
               // byte[] imgBytes = new byte[bytesLength];
                System.out.println("Point 3.5");
                //channel.read(imgBytes);
                System.out.println("Point 3.6");
                //InputStream is = new ByteArrayInputStream(imgBytes);
                System.out.println("Point 3.7");
                
                //var fractalChunk = ImageIO.read(is);
                //graphics.drawImage(fractalChunk, xChunk, yChunk, null);
                
                System.out.println("Processed chunk (" + xChunk + ", " + yChunk + ")");
//            	var cx = channel.readDouble();
//            	System.out.println(cx);
            }
            System.out.println("Point 4");
            File outputFile = new File("MandelbrotImage.jpg");
            ImageIO.write(fractalImage, "jpg", outputFile);

            System.out.println("Point 5");
            double estimatedTime = (double) (System.nanoTime() - startTime) / 1000000000;
            System.out.println("Time total (excluding IO): " + estimatedTime);
            System.out.println("Point 6");
            
            curtask.end();
        }catch(IOException ex) {}
    }
}
