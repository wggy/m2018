package sw.melody.modules.member.service;

import sw.melody.modules.member.entity.MemberEntity;

import java.util.List;

/***
 * Created by ping on 2018/2/26
 */
public interface MemberService {
    void create(String member, Integer status);

    void deleteByMember(String member);

    MemberEntity queryByMember(String member);
    MemberEntity queryById(Long id);

    List<MemberEntity> queryAllMembers();

    void deleteAllMembers();
}
