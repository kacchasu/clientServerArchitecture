package com.example.aksp4;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequestMapping("/")
public class WebsController {

    private final AtomicInteger userId = new AtomicInteger(0);

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "Для подписки на топик перейдите по эндпоинту /webs";
    }

    @GetMapping("/webs")
    public ModelAndView webs() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("webs.html");
        return modelAndView;
    }

    @MessageMapping("/webs")
    @SendTo("/topic/webs-topic")
    public OutputMessage send(Message message)  {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return new OutputMessage("incognito #" + this.userId.incrementAndGet(), message.getText(), time);
    }
}