package com.binfast.apijson.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 刘斌
 * @date 2022/12/4 5:07 下午
 */
@Controller
public class TableController {

    @RequestMapping("/table")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("admin.html");
        return view;
    }

//    @RequestMapping("/access")
//    public ModelAndView access() {
//        ModelAndView view = new ModelAndView("admin.html");
//        return view;
//    }
}
