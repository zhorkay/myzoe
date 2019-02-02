package io.myzoe.site.common.controller;

import io.myzoe.config.annotation.WebController;
import org.springframework.web.bind.annotation.RequestMapping;

@WebController
public class IndexController
{
    @RequestMapping({
            "/",
            "/index",
            "/home",
            "/token",
            "/gallery",
            "/signin"
    })
    public String index()
    {
        return "forward:/static/index.html";
    }
}
