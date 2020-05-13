package com.group.project.configuration;

import com.group.project.controller.WebSocket.WebSocketController;
import com.group.project.entity.User;
import com.group.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

@Component
public class ApplicationListenerImpl implements ApplicationListener {

    @Autowired
    UserService userService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    WebSocketController webSocketController;
    @EventListener
    public void onApplicationEvent(SessionDisconnectEvent event) {
        Principal principal = event.getUser();
        System.out.println(principal + "123");
        Integer userId = userService.getIdByName(principal.toString());
        userService.deleteUserById(userId);
        User[] users = userService.getAllUser();
        messagingTemplate.convertAndSend("/topic/users/list", users);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

    }
}
