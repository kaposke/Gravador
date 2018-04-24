package Utils;

import java.io.File;

public class FileReaderUtils {
    // How many .png files are in this folder?
    public int pngCount(File folder) {
        int count = 0;
        for (final File file : folder.listFiles()) {
            if(!file.getPath().endsWith(".png"))
                continue;
            count++;
        }
        return count;
    }

    // What is the latest (highest) index?
    public int getHighestImageIndex(File folder){
        int index = 0;
        for (final File file : folder.listFiles()) {
            int currentIndex = getImageIndex(file);
            index = currentIndex > index ? currentIndex : index;
        }
        return index;
    }

    // What is this specific image's index?
    public int getImageIndex(File image) {
        if(!image.getPath().endsWith(".png"))
            System.out.println("Utils.FileReaderUtils: getImageIndex -> expected a .png format");;
        int from = image.getPath().lastIndexOf('_') + 1;
        int to = image.getPath().lastIndexOf(".png");
        return Integer.parseInt(image.getPath().substring(from, to));
    }
}
