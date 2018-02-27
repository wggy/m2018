package sw.melody.modules.member.mapper;

import org.apache.ibatis.annotations.*;
import sw.melody.modules.member.entity.MemberEntity;

import java.util.List;

/***
 * Created by ping on 2018/2/27
 */
@Mapper
public interface MemberMapper {
    @Insert("INSERT INTO sw_member(member, status) VALUES(#{member}, #{status})")
    void create(@Param("member") String member, @Param("status") Integer status);

    @Delete("DELETE FROM sw_member WHERE member =#{member}")
    void deleteByMember(@Param("member") String member);

    @Select("SELECT * FROM sw_member WHERE member = #{member}")
    MemberEntity queryByMember(@Param("member") String member);

    @Select("SELECT * FROM sw_member WHERE id = #{id}")
    MemberEntity queryById(Long id);

    @Select("SELECT * FROM sw_member")
    List<MemberEntity> queryAllMembers();

    @Delete("DELETE FROM sw_member")
    void deleteAllMembers();

    @Delete("DELETE FROM sw_member WHERE id =#{id}")
    void deleteById(Long id);
}
