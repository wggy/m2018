package com.wggy.prune.book.controller;

import com.wggy.prune.rbac.model.UserEntity;
import com.wggy.prune.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ping
 * @create 2019-02-22 17:18
 **/
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/saveUser/{firstName}/{lastName}/{idCard}/{date}",method = RequestMethod.GET)
    @Transactional
    public UserEntity saveUser(@PathVariable String firstName,
                               @PathVariable String lastName,
                               @PathVariable String idCard,
                               @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        UserEntity user = new UserEntity();
        return userService.save(user);
    }

    /*****************************Read***** ********************************/
    @RequestMapping(value = "/findByAndSort/{lastName}", method = RequestMethod.GET)
    public List<UserEntity> findByAndSort(@PathVariable String lastName) {
        Sort sort = new Sort(Sort.Direction.DESC, "firstName");
        return userService.findByAndSort(lastName, sort);
    }

    @RequestMapping(value = "/findByIdCard/{idCard}", method = RequestMethod.GET)
    public List<UserEntity> findByIdCard(@PathVariable(name = "idCard")  String idCard) {
        return userService.findByIdCard(idCard);
    }

    @RequestMapping(value = "/findByIdCard2/{idCard}", method = RequestMethod.GET)
    public UserEntity findByIdCard2(@PathVariable(name = "idCard") String idCard) {
        return userService.findByIdCard2(idCard);
    }

    @RequestMapping(value = "/findByIdCard3/{idCard}", method = RequestMethod.GET)
    public List<UserEntity> findByIdCard3(@PathVariable(name = "idCard") String idCard) {
        return userService.findByIdCard3(idCard);
    }

    @RequestMapping(value = "/findByLastNameWithPageable/{lastName}", method = RequestMethod.GET)
    public Page<UserEntity> findByLastNameWithPageable(@PathVariable String lastName) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(5,2,sort);
        return userService.findByLastNameWithPageable(lastName, pageable);
    }

    @RequestMapping(value = "/findByFirstNameOrLastName/{firstName}/{lastName}", method = RequestMethod.GET)
    public List<UserEntity> findByFirstNameOrLastName(@PathVariable String firstName,
                                                @PathVariable String lastName) {
        return userService.findByFirstNameOrLastName(firstName, lastName);
    }

    @RequestMapping(value = "/findByDateOfBirth/{date}", method = RequestMethod.GET)
    public List<UserEntity> findByDateOfBirth(@PathVariable(name = "date")
                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        return userService.findByCreateTime(date);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public List<UserEntity> findAll() {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        return userService.findAll(sort);
    }
    /*****************************update*************************************/
    @RequestMapping(value = "/updateUser/{firstName}/{idCard}", method = RequestMethod.GET)
    public int updateUser(@PathVariable String firstName, @PathVariable String idCard) {
        return userService.updateUserEntity(firstName, idCard);
    }
    /*****************************delete*************************************/
    @RequestMapping(value = "/deleteByIdCard/{idCard}", method = RequestMethod.GET)
    @Transactional
    public String deleteByIdCard(@PathVariable String idCard) {
        userService.deleteByIdCard(idCard);
        return "SUCCESS";
    }

    @RequestMapping(value = "/deleteByIdCard2/{idCard}", method = RequestMethod.GET)
    @Transactional
    public String deleteByIdCard2(@PathVariable String idCard) {
        userService.deleteByIdCard2(idCard);
        return "SUCCESS";
    }

    @RequestMapping(value = "/deleteById/{id}", method = RequestMethod.GET)
    @Transactional
    public String deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return "SUCCESS";
    }
}
