package com.wggy.prune.rbac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ping
 * @create 2019-04-02 17:04
 **/
@Controller
public class PageController {

    @RequestMapping("{folder}/{fileName}.html")
    public String page(@PathVariable("folder") String folder, @PathVariable("fileName") String fileName) {
        return folder + "/" + fileName + ".html";
    }

    @RequestMapping("{fileName}.html")
    public String page(@PathVariable("fileName") String fileName) {
        return fileName + ".html";
    }
}
