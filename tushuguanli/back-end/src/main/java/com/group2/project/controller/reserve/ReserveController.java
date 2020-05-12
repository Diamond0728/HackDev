package com.group2.project.controller.reserve;

import com.group2.project.common.ResponseDomain;
import com.group2.project.entity.Book;
import com.group2.project.entity.Bookinfo;
import com.group2.project.entity.BorrowReturn;
import com.group2.project.entity.Reserve;
import com.group2.project.service.BookinfoService;
import com.group2.project.service.ReserveService;
import com.group2.project.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value = "/api/reserve")
public class ReserveController{
    @Autowired
    ReserveService reserveService;
    @Autowired
    UserService userService;
    @Autowired
    BookinfoService bookinfoService;
    @RequestMapping(value = "/one",method = RequestMethod.POST)
    public ResponseDomain one(@RequestBody Reserve reserve) {
        ResponseDomain responseDomain = new ResponseDomain();
        try {
            Subject currentUser = SecurityUtils.getSubject();
            String currentUserName = String.valueOf(currentUser.getPrincipal());
            Integer u_id = userService.getIdByName(currentUserName);
            String reservetime = String.valueOf(new Date().getTime());
            String deadtime = String.valueOf(Long.valueOf(reservetime) + 864000000);
            reserve.setU_id(u_id);
            reserve.setReservetime(reservetime);
            reserve.setDeadline(deadtime);
            reserveService.insertOne(reserve);
        } catch (Exception e) {
            e.printStackTrace();
            responseDomain.fail("服务器繁忙，请稍后重试");
        }
        return responseDomain;
    }
    @RequestMapping(value = "/is_reserve",method = RequestMethod.POST)
    public ResponseDomain isReserve(@RequestBody Reserve reserve) {
        ResponseDomain responseDomain = new ResponseDomain();
        try {
            Subject currentUser = SecurityUtils.getSubject();
            String currentUserName = String.valueOf(currentUser.getPrincipal());
            Integer u_id = userService.getIdByName(currentUserName);
            reserve.setU_id(u_id);
            Reserve[] reserves = reserveService.find(reserve);
            boolean flag = false;
            String time = String.valueOf(new Date().getTime());
            for(int i=0; i>reserves.length; i++) {
                if(Long.valueOf(reserves[i].getDeadline()) > Long.valueOf(time)) {
                    flag = true;
                }
            }
            if (!flag) {
                responseDomain.fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDomain.fail("没预约过这本书");
        }
        return responseDomain;
    }

    @RequestMapping(value = "/his")
    public ResponseDomain his() {
        ResponseDomain responseDomain = new ResponseDomain();
        try {
            Subject currentUser = SecurityUtils.getSubject();
            String currentUserName = String.valueOf(currentUser.getPrincipal());
            Integer u_id = userService.getIdByName(currentUserName);
            Reserve[] reserves = reserveService.getByUid(u_id);
            List list = new LinkedList();
            for(int i=0; i< reserves.length; i++) {
                Bookinfo bookinfo = bookinfoService.getOneByIsbn(reserves[i].getIsbn());
                Map map = new HashMap();
                map.put("reserve", reserves[i]);
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
