import Utils.FileReaderUtils;
import org.jcodec.api.awt.AWTSequenceEncoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.Rational;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Encoder extends FileReaderUtils {
    public void encodeSequence(File path, File outPath) throws IOException {
        List<BufferedImage> images = populateImages(path);
        SeekableByteChannel out = null;
        try {
            out = NIOUtils.writableFileChannel(outPath.getPath());
            AWTSequenceEncoder encoder = new AWTSequenceEncoder(out, Rational.R(25, 1));

            int current = 0;
            for (BufferedImage image : images) {
                if(image == null)
                    continue;
                encoder.encodeImage(image);
                System.out.println("Encoding video");
                System.out.println("Progress: " + ((float)current / (float)images.size()) * 100.0f + "%");
                current++;
            }
            encoder.finish();
        } finally {
            NIOUtils.closeQuietly(out);
        }
    }

    private List<BufferedImage> populateImages(File folder) throws IOException {
        List<BufferedImage> images = new ArrayList<BufferedImage>();
        int size = pngCount(folder);
        for (int i = 0; i < size; i++) {
            images.add(null);
        }
        System.out.println("Size: " + size);

        int current = 1;
        for (final File fileEntry : folder.listFiles()) {
            int index = getImageIndex(fileEntry);
            BufferedImage image = ImageIO.read(fileEntry);
            images.set(index - 1, image);

            System.out.println("Loading images");
            System.out.println("Progress: " + (int)(((float)current / (float)size) * 100.0f) + "%");
            current++;
        }
        return images;
    }
}
