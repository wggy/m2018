package sw.melody.vm_chapter7;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author ping
 * @create 2018-11-29 16:30
 **/

public class ClassLoaderTest {
    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                String filename = name.substring(name.lastIndexOf(".") + 1) + ".class";
                InputStream in  = getClass().getResourceAsStream(filename);
                if (in == null) {
                    return super.loadClass(name);
                }
                try {
                    byte[] bytes = new byte[in.available()];
                    in.read(bytes);
                    return defineClass(name, bytes, 0, bytes.length);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj = classLoader.loadClass("sw.melody.vm_chapter7.ClassLoaderTest").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof sw.melody.vm_chapter7.ClassLoaderTest);
    }
}
