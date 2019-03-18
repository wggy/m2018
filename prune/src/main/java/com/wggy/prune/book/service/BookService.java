package com.wggy.prune.book.service;

import com.wggy.prune.book.model.BookEntity;

import java.util.List;

/**
 * @author ping
 * @create 2019-02-22 16:33
 **/

public interface BookService {
    List<BookEntity> queryList(String reader);
}
