package com.imooc.controller;

import com.immoc.mapper.StuMapper;
import com.imooc.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StuController {
    @Autowired
    private StuService stuService;
    @GetMapping("/getStu")
    public Object getStu(int id){
        return stuService.getStuInfo(id);
    }

    @PostMapping("/saveStu")
    public Object saveStu( ){
        stuService.saveStu();
        return "save OK";
    }
    @PostMapping("/updateStu")
    public Object updateStu(int id){
        stuService.updateStu(id);
        return "update OK";
    }
    @PostMapping("/delStu")
    public Object deleteStu(int id){
        stuService.deleteStu(id);
        return "delete Ok";
    }
}
