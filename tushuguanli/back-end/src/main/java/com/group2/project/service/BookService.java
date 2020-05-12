package com.group2.project.service;

import com.group2.project.entity.Book;
import org.springframework.stereotype.Service;

@Service
public interface BookService {
    void insert(Book book);
    void updateStatus(Book book);
    Book[] selectByIsbn(Book book);
    Book getOne(Integer b_id);
}
