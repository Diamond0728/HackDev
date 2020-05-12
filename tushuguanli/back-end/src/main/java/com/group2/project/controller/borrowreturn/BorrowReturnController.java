package com.group2.project.controller.borrowreturn;

import com.group2.project.common.ResponseDomain;
import com.group2.project.common.SendEmail;
import com.group2.project.entity.*;
import com.group2.project.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class BorrowReturnController {
    @Autowired
    BorrowReturnService borrowReturnService;
    @Autowired
    BookService bookService;
    @Autowired
    UserService userService;
    @Autowired
    BookinfoService bookinfoService;
    @Autowired
    ReserveService reserveService;
    @RequestMapping(value = "/api/back/bybid")
    public ResponseDomain back(@RequestBody BorrowReturn borrowReturn){
        ResponseDomain responseDomain = new ResponseDomain();
        try {
            BorrowReturn[] borrowReturns = borrowReturnService.getBorrowNotReturn(borrowReturn.getB_id());
            borrowReturns[0].setRtime(String.valueOf(new Date().getTime()));
            borrowReturnService.updateRtimeByBrid(borrowReturns[0]);
            Book book = new Book();
            book.setB_id(String.valueOf(borrowReturn.getB_id()));
            book.setStatus("未借出");
            bookService.updateStatus(book);
            // 发邮件
            SendEmail sendEmail = new SendEmail();
            Book book1 = bookService.getOne(borrowReturn.getB_id());
            Bookinfo bookinfo = bookinfoService.getOneByIsbn(book1.getIsbn());
            String time = String.valueOf(new Date().getTime());
            Reserve[] reserves = reserveService.getByIsbn(book1.getIsbn());
            boolean flag = false;
            for(int i=0; i<reserves.length; i++) {
                if(Long.valueOf(reserves[i].getDeadline()) > Long.valueOf(time)) {
                    flag = true;
                    try {
                        User user = userService.getOneByUid(reserves[i].getU_id());
                        String text = "你预约的书籍" + bookinfo.getB_name() + "到了，赶快去图书馆借阅吧。";
                        System.out.println(text);
                        System.out.println(user.getEmail());
                        sendEmail.send(user.getEmail(), "预约的书到了！", text);
                    } catch (Exception f) {
                        f.printStackTrace();
                    }
                }
            }
            if (flag) {
                book.setStatus("已预约");
                bookService.updateStatus(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDomain.fail("出了点小问题");
        }
        return responseDomain;
    }

    @RequestMapping(value = "/api/borrow/one")
    public ResponseDomain borrowOne(@RequestBody BorrowReturn borrowReturn) {
        ResponseDomain responseDomain = new ResponseDomain();
        try {
            String btime = String.valueOf(new Date().getTime());
            borrowReturn.setBtime(btime);
            borrowReturnService.borrow(borrowReturn);
            Book book = new Book();
            book.setB_id(String.valueOf(borrowReturn.getB_id()));
            book.setStatus("已借出");
            bookService.updateStatus(book);
            Reserve reserve = new Reserve();
            Book book1 = bookService.getOne(Integer.valueOf(book.getB_id()));
            reserve.setU_id(borrowReturn.getU_id());
            reserve.setIsbn(book1.getIsbn());
            reserve.setDeadline(btime);
            reserveService.update(reserve);
        } catch (Exception e) {
            e.printStackTrace();
            responseDomain.fail("服务器繁忙");
        }
        return responseDomain;
    }

    @RequestMapping(value = "/api/borrow/his")
    public ResponseDomain his() {
        ResponseDomain responseDomain = new ResponseDomain();
        try {
            Subject currentUser = SecurityUtils.getSubject();
            String currentUserName = String.valueOf(currentUser.getPrincipal());
            Integer u_id = userService.getIdByName(currentUserName);
            BorrowReturn[] borrowReturns = borrowReturnService.getBorrowReturn(u_id);
            List list = new LinkedList();
            for(int i=0; i< borrowReturns.length; i++) {
                System.out.println(borrowReturns[i].getBr_id());
                Integer b_id = borrowReturns[i].getB_id();
                Book book = bookService.getOne(b_id);
                Bookinfo bookinfo = bookinfoService.getOneByIsbn(book.getIsbn());
                Map map = new HashMap();
                map.put("borrowReturn", borrowReturns[i]);
                map.put("bookinfo", bookinfo);
                list.add(map);
            }
            responseDomain.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            responseDomain.fail();
        }
        return  responseDomain;
    }
}
