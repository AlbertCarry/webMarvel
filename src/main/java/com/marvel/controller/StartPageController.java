package com.marvel.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/")
@Api(value = "start page")
public class StartPageController {

    @GetMapping()
    @ApiOperation(value = "Return start page")
    public String showStartPage(){
        return "startpage/startpage";
    }



}
