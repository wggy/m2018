package com.wggy.prune.book.service.impl;

import com.wggy.prune.book.model.UserEntity;
import com.wggy.prune.book.repository.UserRepository;
import com.wggy.prune.book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
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
    public List<UserEntity> findByIdCard(String idCard) {
        return userRepository.findByIdCard(idCard);
    }

    @Override
    public UserEntity findByIdCard2(String idCard) {
        return userRepository.findByIdCard2(idCard);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
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
    public List<UserEntity> findByDateOfBirth(LocalDate date) {
        return userRepository.findByDateOfBirth(date);
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
        userRepository.deleteByIdCard(idCard);
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
