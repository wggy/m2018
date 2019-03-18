package sw.melody.deadlock;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ping
 * @create 2019-02-15 14:43
 **/
@Slf4j
public class CooperateCallDeadlock {

    private static class Taxi {
        private String location;
        private String destination;
        private Dispatcher dispatcher;

        public Taxi(Dispatcher dispatcher, String destination) {
            this.dispatcher = dispatcher;
            this.destination = destination;
        }

        public synchronized String getLocation() {
            log.info("获取锁Taxi：getLocation");
            return location;
        }

        public synchronized void setLocation(String location) {
            log.info("获取锁Taxi：setLocation");
            this.location = location;
            if (location.equals(destination)) {
                dispatcher.notifyAvailable(this);
            }
        }
    }

    private static class Dispatcher {
        private Set<Taxi> availableTaxis;

        public Dispatcher() {
            availableTaxis = new HashSet<>();
        }

        public synchronized void notifyAvailable(Taxi taxi) {
            log.info("获取锁Dispatcher：notifyAvailable");
            availableTaxis.add(taxi);
        }

        public synchronized void driveTaxis() {
            log.info("获取锁Dispatcher：driveTaxis");
            for (Taxi t : availableTaxis) {
                log.info("开到：【{}】", t.getLocation());
            }
        }
    }

    public static void main(String[] args) {
        Dispatcher dispatcher = new Dispatcher();
        Taxi A = new Taxi(dispatcher, "LA");
        new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                A.setLocation("LA");
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                dispatcher.driveTaxis();
            }
        }).start();
    }


}
