package sw.melody.vm_chapter7;

/**
 * @author ping
 * @create 2018-11-28 17:41
 **/

public class SuperClass {
    public static int value = 123;

    static{
        System.out.println("this is static block");
    }
    //普通代码块
    {
        System.out.println("this is normal block");
    }
    //默认构造函数
    public SuperClass(){
        System.out.println("this is SuperClass constructor");
    }
}
