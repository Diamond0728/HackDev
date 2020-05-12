package com.group2.project.controller.enter;

import com.group2.project.common.ResponseDomain;
import com.group2.project.entity.Book;
import com.group2.project.entity.Bookinfo;
import com.group2.project.entity.User;
import com.group2.project.service.impl.BookServiceImpl;
import com.group2.project.service.impl.BookinfoServiceImpl;
import com.group2.project.service.impl.UserServiceImpl;
import com.group2.project.shiro.AppShiroUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(value = "/api/enter")
public class EnterController {
    @Autowired
    BookinfoServiceImpl bookInfoService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    BookServiceImpl bookService;

    @RequiresRoles("officer")
    @RequestMapping(value = "/find",method = RequestMethod.GET)
    public ResponseDomain find(@RequestParam("isbn") String isbn){
        ResponseDomain responseDomain = new ResponseDomain();
        System.out.println(isbn);
        try {
            Bookinfo bookInfo = bookInfoService.getOneByIsbn(isbn);
            responseDomain.setData(bookInfo);
        } catch (Exception e) {
            responseDomain.fail("服务器繁忙，稍后重试");
        }
        return responseDomain;
    }

    @RequiresRoles("officer")
    @RequestMapping(value = "/add/bookinfo",method = RequestMethod.POST)
    public ResponseDomain find(@RequestBody Bookinfo bookinfo){
        ResponseDomain responseDomain = new ResponseDomain();
        try {
            Subject currentUser = SecurityUtils.getSubject();
            String currentUserName = String.valueOf(currentUser.getPrincipal());
            Integer u_id = userService.getIdByName(currentUserName);
            bookinfo.setAgent_id(u_id);
            bookInfoService.insert(bookinfo);
        } catch (Exception e) {
            responseDomain.fail("插入失败");
        }
        Subject currentUser = SecurityUtils.getSubject();
        System.out.println(currentUser.getPrincipal().getClass());
        return responseDomain;
    }

    @RequiresRoles("officer")
    @RequestMapping(value = "/add/book",method = RequestMethod.POST)
    public ResponseDomain addBook(@RequestBody Book book) {
        ResponseDomain responseDomain = new ResponseDomain();
        try {
            bookService.insert(book);
        } catch (Exception e) {
            responseDomain.fail("插入数据库失败");
        }
        return responseDomain;
    }
}
