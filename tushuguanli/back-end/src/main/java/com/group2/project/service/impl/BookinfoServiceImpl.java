package com.group2.project.service.impl;

import com.group2.project.entity.Bookinfo;
import com.group2.project.mapper.BookinfoMapper;
import com.group2.project.service.BookinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookinfoServiceImpl implements BookinfoService {
    @Autowired
    BookinfoMapper bookinfoMapper;

    @Override
    public Bookinfo getOneByIsbn(String isbn) {
        return bookinfoMapper.getOneByIsbn(isbn);
    }

    @Override
    public void insert(Bookinfo bookinfo) {
        bookinfoMapper.insert(bookinfo);
    }

    @Override
    public Bookinfo[] getByPage(Integer page, Integer count) {
        return bookinfoMapper.getByPage((page-1)*count,count);
    }

    @Override
    public Bookinfo[] getByName(String b_name) {
        return bookinfoMapper.getByName("%"+b_name+"%");
    }

}
