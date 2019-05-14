package com.wggy.prune.rbac.service.impl;

import com.wggy.prune.PruneApplication;
import com.wggy.prune.rbac.model.UserEntity;
import com.wggy.prune.rbac.repository.UserRepository;
import com.wggy.prune.rbac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ping
 * @create 2019-02-22 17:05
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public List<UserEntity> findByAndSort(String lastName, Sort sort) {
        return userRepository.findByAndSort(lastName, sort);
    }

    @Override
    public List<UserEntity> findByIdCard(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserEntity findByIdCard2(String idCard) {
        return userRepository.findByIdCard2(idCard);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername2(username);
    }

    @Override
    public List<UserEntity> findByIdCard3(String idCard) {
        return userRepository.findByIdCard3(idCard);
    }

    @Override
    public Page<UserEntity> findByLastNameWithPageable(String lastName, Pageable pageable) {
        return userRepository.findByLastNameWithPageable(lastName, pageable);
    }

    @Override
    public List<UserEntity> findByFirstNameOrLastName(String firstName, String lastName) {
        return userRepository.findByFirstNameOrLastName(firstName, lastName);
    }

    @Override
    public List<UserEntity> findByCreateTime(LocalDateTime date) {
        return userRepository.findByCreateTime(date);
    }

    @Override
    public List<UserEntity> findAll(Sort sort) {
        return userRepository.findAll(sort);
    }

    @Override
    public int updateUserEntity(String firstName, String idCard) {
        return userRepository.updateUserEntity(firstName, idCard);
    }

    @Override
    public void deleteByIdCard(String idCard) {
        userRepository.deleteByEmail(idCard);
    }

    @Override
    public void deleteByIdCard2(String idCard) {
        userRepository.deleteByIdCard2(idCard);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
