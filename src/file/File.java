package file;

import java.io.FileNotFoundException;
import java.io.IOException;

public final class File {
    private File() {}

    public static String getExtension(String filePath) throws IOException
    {
        if (filePath.indexOf('.') < 0 || filePath.indexOf('.') == filePath.length() - 1)
            throw new FileNotFoundException("Incorrect file name input");

        return filePath.substring(filePath.lastIndexOf('.') + 1);
    }
}
