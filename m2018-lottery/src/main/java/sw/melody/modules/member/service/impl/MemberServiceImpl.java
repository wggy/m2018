package sw.melody.modules.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import sw.melody.modules.member.entity.MemberEntity;
import sw.melody.modules.member.service.MemberService;

import java.util.List;

/***
 * Created by ping on 2018/2/26
 */
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public void create(String member, Integer status) {
        jdbcTemplate.update("insert into sw_member(member, status) values(?, ?)", member, status);
    }

    @Override
    public void deleteByMember(String member) {
        jdbcTemplate.update("delete from sw_member where member = ?", member);
    }

    @Override
    public MemberEntity queryByMember(String member) {
        return jdbcTemplate.queryForObject("select * from sw_member where member = ?", MemberEntity.class, member);
    }

    @Override
    public MemberEntity queryById(Long id) {
        return jdbcTemplate.queryForObject("select * from sw_member where id = ?", MemberEntity.class, id);
    }

    @Override
    public List<MemberEntity> queryAllMembers() {
        return jdbcTemplate.queryForList("select * from sw_member", MemberEntity.class);
    }

    @Override
    public void deleteAllMembers() {
        jdbcTemplate.update("delete from sw_member");
    }
}
