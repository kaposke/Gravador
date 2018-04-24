import Utils.FileReaderUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Recorder extends FileReaderUtils {

    private float delay = 1.0f;
    private String path;
    private String fileName;
    private int shotCount = 0;
    private boolean isRecording = false;

    private float startTime = 0;

    Recorder(String path, String fileName, float delaySeconds) {
        this.path = path + "/";
        this.fileName = fileName;
        this.delay = delaySeconds;

        File folder = new File(path);
        if (!folder.exists())
            folder.mkdir();
    }

    Recorder(String path, float delaySeconds) {
        this(path, "Screenshot", delaySeconds);
    }

    public void startRecording() {
        shotCount = getHighestImageIndex(new File(path));
        isRecording = true;
        startTime = System.nanoTime();
    }

    public void update() {
        if(isRecording)
            record();
    }

    private void record() {
        if((System.nanoTime() - startTime) / 1000000000.0f >= delay) {
            takePrint(fileName + "_" + ++shotCount);
            startTime = System.nanoTime();
        }
    }

    private void takePrint(String fileName) {
        try {
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())); // Takes the print
            ImageIO.write(image, "png", new File(path + fileName +".png")); // Stores it in a .png file
            System.out.println("Print saved on: " + path + fileName + ".png");
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() {
        isRecording = false;
    }

    public void setDelay(float seconds) {
        this.delay = seconds;
    }

    public void setPath(String path) {
        this.path = path + "/";
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isRecording() {
        return isRecording;
    }
}
