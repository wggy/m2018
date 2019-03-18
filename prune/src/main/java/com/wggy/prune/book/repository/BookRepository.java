package com.wggy.prune.book.repository;

import com.wggy.prune.book.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ping
 * @create 2019-02-22 15:09
 **/
@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @Query(value = "select * from book where reader = ?1", nativeQuery = true)
    List<BookEntity> findList(String reader);
}
