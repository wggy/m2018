package sw.melody.common.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/***
 * Created by ping on 2018-7-12
 */
public class IOUtils {

    public static File uploadToFile(MultipartFile file, String path) {
        Path p = Paths.get(path);
        try {
            Files.createDirectories(p.getParent());
            File newFile = p.toFile();
            file.transferTo(newFile);
            return newFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
