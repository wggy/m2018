package sw.melody.vm_chapter7;

/**
 * @author ping
 * @create 2018-11-28 17:41
 **/

public class SuperClass {
    public static int value = 123;

    static {
        System.out.println("SuperClass init");
        System.out.println(value);
    }
}
