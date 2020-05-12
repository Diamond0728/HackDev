package com.group2.project.controller.book;

import com.group2.project.common.ResponseDomain;
import com.group2.project.entity.Book;
import com.group2.project.service.BookService;
import com.group2.project.service.impl.BookinfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/book")
public class BookController {
    @Autowired
    BookinfoServiceImpl bookInfoService;
    @Autowired
    BookService bookService;

    @RequestMapping(value = "/bookinfo/list",method = RequestMethod.GET)
    public ResponseDomain bookInfoList() {
        ResponseDomain responseDomain = new ResponseDomain();
        try {
            responseDomain.setData(bookInfoService.getByPage(1,10));
        } catch (Exception e) {
            responseDomain.fail("系统繁忙,稍后重试");
        }
        return responseDomain;
    }

    @RequestMapping(value = "/isbn",method = RequestMethod.POST)
    public ResponseDomain booksByIsbn(@RequestBody Book book) {
        ResponseDomain responseDomain = new ResponseDomain();
        try {
            Book[] books = bookService.selectByIsbn(book);
            responseDomain.setData(books);
        } catch (Exception e) {
            responseDomain.fail("系统繁忙,稍后重试");
        }
        return responseDomain;
    }

    @RequestMapping(value = "/bookinfo/list/byname",method = RequestMethod.GET)
    public ResponseDomain bookInfoList(@RequestParam("b_name") String b_name) {
        ResponseDomain responseDomain = new ResponseDomain();


            responseDomain.setData(bookInfoService.getByName(b_name));

        return responseDomain;
    }
}
