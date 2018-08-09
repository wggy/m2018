package sw.melody.common.utils;

import org.springframework.util.StringUtils;

/**
 * 常量
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月15日 下午1:23:52
 */
public class Constant {
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;

	/**
	 * 菜单类型
	 * 
	 * @author chenshun
	 * @email sunlightcs@gmail.com
	 * @date 2016年11月15日 下午1:24:29
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        private MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     * 
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        private ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3);

        private int value;

        private CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public enum SampleStatus {

        Running("running"),
        Success("success"),
        Fail("fail");

        SampleStatus(String status) {
            this.status = status;
        }
        private String status;

        public String getStatus() {
            return status;
        }
    }

    public enum DataType {
        Snp("snp"),
        Indel("indel");

        DataType(String type) {
            this.type = type;
        }
        private String type;
    }

    /***
     * 基因突变类型
     */
    public enum MutationMode {
        M_0_0("0/0", "hom"),
        M_0_1("0/1", "hex"),
        M_1_1("1/1", "hom"),
        ;

        MutationMode(String val, String mode) {
            this.val = val;
            this.mode = mode;
        }

        public static String getMode(String val) {
            if (StringUtils.isEmpty(val)) {
                return null;
            }
            for (MutationMode mm : MutationMode.values()) {
                if (mm.val.equals(val)) {
                    return mm.mode;
                }
            }
            return null;
        }

        private String val;
        private String mode;
    }
}
