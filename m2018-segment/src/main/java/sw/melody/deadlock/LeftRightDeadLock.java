package sw.melody.deadlock;

/**
 * @author ping
 * @create 2019-02-15 10:17
 **/

public class LeftRightDeadLock {

    private static Object left = new Object();
    private static Object right = new Object();

    public static void leftRigth() {
        synchronized (left) {
            System.out.println("leftRigth: left lock");
            synchronized (right) {
                System.out.println("leftRigth: right lock");
            }
        }
    }

    public static void rightLeft() {
        synchronized (right) {
            System.out.println("rightLeft: right lock");
            synchronized (left) {
                System.out.println("rightLeft: left lock");
            }
        }
    }

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i=0; i<100; i++) {
                leftRigth();
            }
        }).start();
        new Thread(() -> {
            for (int i=0; i<100; i++) {
                rightLeft();
            }
        }).start();
    }

}
