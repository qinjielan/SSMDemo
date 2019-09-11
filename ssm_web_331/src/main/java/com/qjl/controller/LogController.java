package com.qjl.controller;


import com.qjl.domain.SysLog;
import com.qjl.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/log")
public class LogController {

    @Autowired
    LogService logService;

    @RequestMapping("/findAll")
    public ModelAndView findAllToLog(){
        List<SysLog> sysLogs = logService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("sysLogs",sysLogs);
        modelAndView.setViewName("syslog-list");
        return modelAndView;
    }
}
