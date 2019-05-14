package com.wggy.prune.rbac.repository;

import com.wggy.prune.rbac.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Override
    UserEntity save(UserEntity UserEntity);
    /******************************read***************************************/


    @Query(value = "select * from pru_user u where u.username = ?1 limit 1", nativeQuery = true)
    UserEntity findByUsername2(String username);

    /**
     * Using sort
     * @param lastName
     * @param sort
     * @return
     */
    @Query("select u from UserEntity u where u.createTime like ?1%")
    List<UserEntity> findByAndSort(String lastName, Sort sort);

    /**
     * Query creation
     * this translates into the following query:
     * select u from UserEntity u where u.username = ?1
     */
    List<UserEntity> findByUsername(String username);

    /**
     * Native Queries
     * The @Query annotation allows for running native queries by setting the nativeQuery flag to true
     * @param email
     * @return
     */
    @Query(value = "select * from pru_user u where u.email = ?1 limit 1", nativeQuery = true)
    UserEntity findByIdCard2(String email);

    /**
     * Using @Query
     * @param email
     * @return
     */
    @Query("select u from UserEntity u where u.email = ?1")
    List<UserEntity> findByIdCard3(String email);

    /**
     * Declare native count queries for pagination at the query method by using @Query
     * @param email
     * @param pageable
     * @return
     */
    @Query(value = "SELECT * FROM pru_user u WHERE u.email = ?1",
            countQuery = "SELECT count(*) FROM pru_user u WHERE u.email = ?1",
            nativeQuery = true)
    Page<UserEntity> findByLastNameWithPageable(String email, Pageable pageable);

    /**
     * Using Named Parameters
     * @param firstName
     * @param email
     * @return
     */
    @Query("select u from UserEntity u where u.username = :username or u.email = :email")
    List<UserEntity> findByFirstNameOrLastName(@Param("username") String firstName, @Param("email") String email);

    /**
     * findByCreateTime
     * @param date
     * @return
     */
    List<UserEntity> findByCreateTime(@Param("date") LocalDateTime date);

    /**
     * find all UserEntitys
     * @param sort
     * @return
     */
    @Override
    List<UserEntity> findAll(Sort sort);
    /****************************update*****************************************/
    /**
     * update a usr by Modifying Queries
     * @param username
     * @param email
     * @return
     */
    @Modifying
    @Query("update UserEntity u set u.username = ?1 where u.email = ?2")
    int updateUserEntity(String username, String email);
    /****************************delete*****************************************/
    /**
     * delete a UserEntity by idCard
     * @param email
     */
    void deleteByEmail(String email);

    /**
     * Using a derived delete query
     * @param idCard
     */
    @Modifying
    @Query("delete from UserEntity u where u.email = ?1")
    void deleteByIdCard2(String idCard);

    /**
     * delete a UserEntity by id
     * @param id
     */
    @Override
    @Modifying
    @Query(value = "delete from pru_user where id = ?1", nativeQuery = true)
    void deleteById(Long id);
}
