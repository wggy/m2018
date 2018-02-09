package sw.melody.modules.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sw.melody.App;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class LotteryPropertyTest {

    @Autowired
    private LotteryProperty lotteryProperty;


    @Test
    public void testProperty() {
        Assert.assertEquals("程序猿Melody", lotteryProperty.getName());
        Assert.assertEquals("使用Spring Boot搭建抽奖系统", lotteryProperty.getTitle());
        Assert.assertEquals("程序猿Melody正在努力写《使用Spring Boot搭建抽奖系统》", lotteryProperty.getDesc());

        log.info("输出随机随机字符串：{}", lotteryProperty.getValue());
        log.info("输出随机随机int：{}", lotteryProperty.getNumber());
        log.info("输出随机随机long：{}", lotteryProperty.getBignumber());
        log.info("输出10以内的随机数：{}", lotteryProperty.getTest1());
        log.info("输出10-20的随机数：{}", lotteryProperty.getTest2());

    }
}