package sw.melody.vm_chapter7;

/**
 * @author ping
 * @create 2018-12-07 9:27
 **/

public class Bike1 {

    private static final Bike1 b1 = new Bike1();
    private static final Bike1 b2 = new Bike1();

    public Bike1() {
        System.out.println("Bike1");
    }

    {
        System.out.println("Bike1 代码块");
    }

    static {
        System.out.println("Bike1 静态块加载");
    }

    public static void main(String[] args) {

    }
}
class MoBike extends Bike1 {


    public MoBike() {
        System.out.println("MoBike");
    }
    static {
        System.out.println("MoBike 静态块");
    }
    {
        System.out.println("MoBike 代码块");
    }
    /**
     * Bike1 代码块
     * Bike1
     * Bike1 代码块
     * Bike1
     * Bike1 静态块加载
     * MoBike 静态块
     * Bike1 代码块
     * Bike1
     * MoBike 代码块
     * MoBike
     */

}