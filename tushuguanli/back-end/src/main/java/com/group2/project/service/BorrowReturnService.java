package com.group2.project.service;

import com.group2.project.entity.BorrowReturn;
import org.springframework.stereotype.Service;

@Service
public interface BorrowReturnService {
    BorrowReturn[] getBorrowNotReturn (Integer b_id);
    void updateRtimeByBrid(BorrowReturn borrowReturn);
    void borrow(BorrowReturn borrowReturn);
    BorrowReturn[] getBorrowReturn(Integer u_id);
}
