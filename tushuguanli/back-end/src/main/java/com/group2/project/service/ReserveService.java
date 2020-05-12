package com.group2.project.service;

import com.group2.project.entity.Reserve;
import com.group2.project.mapper.ReserveMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface ReserveService {
    Reserve[] find(Reserve reserve);
    void insertOne(Reserve reserve);
    Reserve[] getByUid(Integer u_id);
    Reserve[] getByIsbn(String isbn);
    void update(Reserve reserve);
}
