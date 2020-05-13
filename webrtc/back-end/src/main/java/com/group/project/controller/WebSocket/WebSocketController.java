package com.group.project.controller.WebSocket;
import com.group.project.entity.User;
import com.group.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

// 私信聊天的控制器
@RestController
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    UserService userService;

    @MessageMapping("/api/chat")
    //在springmvc 中可以直接获得principal,principal 中包含当前用户的信息
    public void handleChat(Principal principal, MessagePara messagePara) {
        System.out.println(messagePara.getUsername());
        System.out.println(messagePara.getContent());
        String currentUserName = principal.getName();
        System.out.println(currentUserName);

        try {
            messagePara.setFrom(principal.getName());
            System.out.println("from" + messagePara.getFrom());
            Integer u_id = userService.getIdByName(currentUserName);
            messagingTemplate.convertAndSendToUser(messagePara.getUsername(),
                    "/queue/notifications",
                    messagePara);
        } catch (Exception e) {
            // 打印异常
            e.printStackTrace();
        }
    }

    @MessageMapping(value = "/api/entrance")
    @SendTo(value = "/topic/users/list")//当服务端有消息时，会对订阅了@SendTo中的路径的浏览器发送消息
    public User[] say(){
        System.out.println("+++++++++++++");
        User[] users = userService.getAllUser();
        System.out.println(users[0].getUsername());
        System.out.println("+++++++++++++");
        return users;
    }
}


