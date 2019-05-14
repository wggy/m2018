package com.wggy.prune.rbac.service;

import com.wggy.prune.rbac.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface UserService {

    UserEntity save(UserEntity UserEntity);

    List<UserEntity> findByAndSort(String lastName, Sort sort);

    List<UserEntity> findByIdCard(String idCard);

    UserEntity findByIdCard2(String idCard);

    UserEntity findByUsername(String username);

    List<UserEntity> findByIdCard3(String idCard);

    Page<UserEntity> findByLastNameWithPageable(String lastName, Pageable pageable);

    List<UserEntity> findByFirstNameOrLastName(String firstName, String lastName);

    List<UserEntity> findByCreateTime(@Param("date") LocalDateTime date);

    List<UserEntity> findAll(Sort sort);

    int updateUserEntity(String firstName, String idCard);

    void deleteByIdCard(String idCard);

    void deleteByIdCard2(String idCard);

    void deleteById(Long id);

}
