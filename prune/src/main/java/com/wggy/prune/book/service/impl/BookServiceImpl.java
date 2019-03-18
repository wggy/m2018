package com.wggy.prune.book.service.impl;

import com.wggy.prune.book.model.BookEntity;
import com.wggy.prune.book.repository.BookRepository;
import com.wggy.prune.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author ping
 * @create 2019-02-22 16:30
 **/
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<BookEntity> queryList(String reader) {
        return bookRepository.findList(reader);
    }
}
