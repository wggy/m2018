package com.wggy.prune.rbac.controller;

import com.wggy.prune.common.MD5Utils;
import com.wggy.prune.common.Rr;
import com.wggy.prune.common.RrException;
import com.wggy.prune.rbac.model.UserEntity;
import com.wggy.prune.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ping
 * @create 2019-04-01 17:44
 **/
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/checkin")
    public Rr checkIn(String username, String password) {
        RrException.nullException(username, password);
        UserEntity userEntity = userService.findByUsername(username);
        RrException.nullException(userEntity);
        if (!userEntity.getPassword().equals(MD5Utils.MD5Encode(password))) {
            RrException.exception("输入密码错误");
        }
        return Rr.ok();
    }

    @RequestMapping("/checkout")
    public Rr checkOut(String username) {


        return Rr.ok();
    }
}
