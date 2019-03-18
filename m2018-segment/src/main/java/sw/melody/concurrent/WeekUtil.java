package sw.melody.concurrent;

import java.util.concurrent.ForkJoinPool;

/**
 * @author ping
 * @create 2019-03-07 14:33
 **/

public enum WeekUtil {

    Monday {
        @Override
        public String toString() {
            return "星期一";
        }
        @Override
        boolean isRest() {
            return false;
        }
    },
    Tuesday {
        @Override
        public String toString() {
            return "星期二";
        }
        @Override
        boolean isRest() {
            return false;
        }
    },
    Wednesday {
        @Override
        public String toString() {
            return "星期三";
        }
        @Override
        boolean isRest() {
            return false;
        }
    },
    Thursday {
        @Override
        public String toString() {
            return "星期四";
        }
        @Override
        boolean isRest() {
            return false;
        }
    },
    Friday {
        @Override
        public String toString() {
            return "星期五";
        }
        @Override
        boolean isRest() {
            return false;
        }
    },
    Saturday {
        @Override
        public String toString() {
            return "星期六";
        }

        @Override
        boolean isRest() {
            return true;
        }
    },
    Sunday {
        @Override
        public String toString() {
            return "星期天";
        }

        @Override
        boolean isRest() {
            return true;
        }
    };

    abstract boolean isRest();


    public static void main(String[] args) {
    }
}
