package sw.melody.vm_chapter7;

/**
 * @author ping
 * @create 2018-11-28 17:42
 **/

public class SubClass extends SuperClass {
    static {
        System.out.println("SubClass init");
    }

    {
        System.out.println("this is SubClass normal block");
    }
    public SubClass(){
        System.out.println("this is SubClass constructor");
    }
}
