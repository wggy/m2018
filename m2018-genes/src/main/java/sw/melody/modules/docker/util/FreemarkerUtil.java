package sw.melody.modules.docker.util;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;

import java.io.File;
import java.io.IOException;

/**
 * @author ping
 * @create 2018-09-10 18:06
 **/

public class FreemarkerUtil {

    public static void processString(String fileName) throws IOException {
        FileTemplateLoader ftl = new FileTemplateLoader(new File(fileName));
        Configuration cfg = new Configuration();
        cfg.setTemplateLoader(ftl);

    }
}
