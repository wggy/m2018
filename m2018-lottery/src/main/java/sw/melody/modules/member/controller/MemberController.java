package sw.melody.modules.member.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sw.melody.config.GlobalException;
import sw.melody.modules.member.entity.MemberEntity;
import sw.melody.modules.member.mapper.MemberMapper;

import java.util.List;

/***
 * Created by ping on 2018/2/26
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberMapper memberMapper;

    @ApiOperation(value="获取成员列表")
    @RequestMapping(value="/list", method= RequestMethod.GET)
    public List<MemberEntity> getUserList() {
        return memberMapper.queryAllMembers();
    }

    @ApiOperation(value="创建成员", notes="根据Member对象创建成员")
    @ApiImplicitParam(name = "member", value = "成员详细实体member", required = true, dataType = "MemberEntity")
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public String createMember(@RequestBody MemberEntity user) {
        if (StringUtils.isEmpty(user.getMember()) || user.getStatus() == null) {
            throw new GlobalException("参数不能为空");
        }
        memberMapper.create(user.getMember(), user.getStatus());
        return "success";
    }

    @ApiOperation(value="获取成员详细信息", notes="根据url的id来获取成员详细信息")
    @ApiImplicitParam(name = "id", value = "成员ID", required = true, dataType = "Long")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public MemberEntity getMember(@PathVariable Long id) {
        if (id == null) {
            throw new GlobalException("参数不能为空");
        }
        return memberMapper.queryById(id);
    }

    @ApiOperation(value="删除成员", notes="根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        if (id == null) {
            throw new GlobalException("参数不能为空");
        }
        memberMapper.deleteById(id);
        return "success";
    }

}
