package com.group2.project.service.impl;

import com.group2.project.entity.BorrowReturn;
import com.group2.project.mapper.BorrowReturnMapper;
import com.group2.project.service.BorrowReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowReturnServiceImpl implements BorrowReturnService {
    @Autowired
    BorrowReturnMapper borrowReturnMapper;
    public BorrowReturn[] getBorrowNotReturn (Integer b_id) {
        return borrowReturnMapper.getBorrowNotReturn(b_id);
    }

    public void updateRtimeByBrid(BorrowReturn borrowReturn) {
        borrowReturnMapper.updateRtime(borrowReturn);
    }

    @Override
    public void borrow(BorrowReturn borrowReturn) {
        borrowReturnMapper.borrow(borrowReturn);
    }

    @Override
    public BorrowReturn[] getBorrowReturn(Integer u_id) {
        return borrowReturnMapper.getBorrowReturn(u_id);
    }
}
