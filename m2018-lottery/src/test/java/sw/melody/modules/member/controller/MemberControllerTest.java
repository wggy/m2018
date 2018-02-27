package sw.melody.modules.member.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import sw.melody.modules.member.entity.MemberEntity;
import sw.melody.modules.member.mapper.MemberMapper;

import java.util.List;

/***
 * Created by ping on 2018/2/27
 */
@Slf4j
@WebAppConfiguration
@SpringBootTest
@RunWith(SpringRunner.class)
public class MemberControllerTest {
    @Autowired
    private MemberMapper memberMapper;

    @Test
    public void listTest() {
        log.info("清空数据......");
        memberMapper.deleteAllMembers();
        log.info("清空数据完成......");
        List<MemberEntity> list = memberMapper.queryAllMembers();
        log.info("初始列表：{}，开始创建成员......", list);
        memberMapper.create("wangping", 0);
        memberMapper.create("sufen", 0);
        memberMapper.create("wenwen", 0);
        log.info("创建成员完成");
        list = memberMapper.queryAllMembers();
        log.info("查询所有成员：{}", list);
        MemberEntity memberEntity = memberMapper.queryByMember("wangping");
        log.info("查询单个成员wangping ：{}", memberEntity);
    }
}
