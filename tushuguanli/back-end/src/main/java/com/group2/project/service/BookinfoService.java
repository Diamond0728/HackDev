package com.group2.project.service;

import com.group2.project.entity.Bookinfo;
import org.springframework.stereotype.Service;

@Service
public interface BookinfoService {
    Bookinfo getOneByIsbn(String isbn);
    void insert(Bookinfo bookinfo);
    Bookinfo[] getByPage(Integer page, Integer count);
    Bookinfo[] getByName(String b_name);
}
