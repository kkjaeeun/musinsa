package com.musinsa.product.controller;

import com.musinsa.product.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/")
    public ModelAndView home(Model model) {
        return new ModelAndView("/home")
                .addObject("data", homeService.getManageProduct());
    }

    @GetMapping("/home")
    public String home2() {
        return "home";
    }
}
