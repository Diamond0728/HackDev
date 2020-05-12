package com.group2.project.service.impl;

import com.group2.project.entity.Book;
import com.group2.project.mapper.BookMapper;
import com.group2.project.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookMapper bookMapper;

    @Override
    public void insert(Book book) {
        bookMapper.insert(book);
    }

    @Override
    public void updateStatus(Book book) {
        bookMapper.updateStatus(book);
    }

    @Override
    public Book[] selectByIsbn(Book book) {
        return bookMapper.selectByIsbn(book);
    }

    @Override
    public Book getOne(Integer b_id) {
        return bookMapper.getOne(b_id);
    }
}
