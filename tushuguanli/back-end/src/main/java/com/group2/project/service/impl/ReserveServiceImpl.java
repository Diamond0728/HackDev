package com.group2.project.service.impl;

import com.group2.project.entity.BorrowReturn;
import com.group2.project.entity.Reserve;
import com.group2.project.mapper.ReserveMapper;
import com.group2.project.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReserveServiceImpl implements ReserveService {
    @Autowired
    ReserveMapper reserveMapper;
    @Override
    public Reserve[] find(Reserve reserve){
        return reserveMapper.find(reserve);
    }
    @Override
    public void insertOne(Reserve reserve){
        reserveMapper.insertOne(reserve);
    }

    @Override
    public void update(Reserve reserve) {
        reserveMapper.update(reserve);
    }

    @Override
    public Reserve[] getByUid(Integer u_id) {
        return reserveMapper.getByUid(u_id);
    }

    @Override
    public Reserve[] getByIsbn(String isbn) {
        return reserveMapper.getByIsbn(isbn);
    }

}
