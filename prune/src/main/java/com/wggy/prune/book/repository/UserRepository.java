package com.wggy.prune.book.repository;

import com.wggy.prune.book.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Override
    UserEntity save(UserEntity UserEntity);
    /******************************read***************************************/


    @Query(value = "select * from user u where u.username = ?1 limit 1", nativeQuery = true)
    UserEntity findByUsername(String username);

    /**
     * Using sort
     * @param lastName
     * @param sort
     * @return
     */
    @Query("select u from UserEntity u where u.lastName like ?1%")
    List<UserEntity> findByAndSort(String lastName, Sort sort);

    /**
     * Query creation
     * this translates into the following query:
     * select u from UserEntity u where u.idCard = ?1
     */
    List<UserEntity> findByIdCard(String idCard);

    /**
     * Native Queries
     * The @Query annotation allows for running native queries by setting the nativeQuery flag to true
     * @param idCard
     * @return
     */
    @Query(value = "select * from user u where u.id_card = ?1 limit 1", nativeQuery = true)
    UserEntity findByIdCard2(String idCard);

    /**
     * Using @Query
     * @param idCard
     * @return
     */
    @Query("select u from UserEntity u where u.idCard = ?1")
    List<UserEntity> findByIdCard3(String idCard);

    /**
     * Declare native count queries for pagination at the query method by using @Query
     * @param lastName
     * @param pageable
     * @return
     */
    @Query(value = "SELECT * FROM user u WHERE u.last_name = ?1",
            countQuery = "SELECT count(*) FROM user u WHERE u.last_name = ?1",
            nativeQuery = true)
    Page<UserEntity> findByLastNameWithPageable(String lastName, Pageable pageable);

    /**
     * Using Named Parameters
     * @param firstName
     * @param lastName
     * @return
     */
    @Query("select u from UserEntity u where u.firstName = :firstName or u.lastName = :lastName")
    List<UserEntity> findByFirstNameOrLastName(@Param("firstName") String firstName,
                                         @Param("lastName") String lastName);

    /**
     * findByDateOfBirth
     * @param date
     * @return
     */
    List<UserEntity> findByDateOfBirth(@Param("date") LocalDate date);

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
     * @param firstName
     * @param idCard
     * @return
     */
    @Modifying
    @Query("update UserEntity u set u.firstName = ?1 where u.idCard = ?2")
    int updateUserEntity(String firstName, String idCard);
    /****************************delete*****************************************/
    /**
     * delete a UserEntity by idCard
     * @param idCard
     */
    void deleteByIdCard(String idCard);

    /**
     * Using a derived delete query
     * @param idCard
     */
    @Modifying
    @Query("delete from UserEntity u where u.idCard = ?1")
    void deleteByIdCard2(String idCard);

    /**
     * delete a UserEntity by id
     * @param id
     */
    @Override
    @Modifying
    @Query(value = "delete from user where id = ?1", nativeQuery = true)
    void deleteById(Long id);
}
