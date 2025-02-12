package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.Trade;
import com.nnk.springboot.domains.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//import javax.validation.Valid;
//import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("app")
public class LoginController {

    @Autowired private UserRepository userRepository;

    /*
    @GetMapping("login")
    public ModelAndView login() {
        System.out.println("Sig Login controller");
        ModelAndView mav = new ModelAndView();
        mav.addObject("username", "JohnDoe"); // Passing a variable
        mav.addObject("password", "xpass"); // Another variable
        mav.setViewName("login");
        return mav;
    }*/

    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
    @PostMapping("perform-login")
    public String performLogin() {

        System.out.println("PerformLogin Is Called ! OK ---");

        return "login";
    }

    @GetMapping("secure/article-details")
    public ModelAndView getAllUserArticles() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("users", userRepository.findAll());
        mav.setViewName("user/list");
        return mav;
    }

    @GetMapping("error")
    public ModelAndView error() {
        ModelAndView mav = new ModelAndView();
        String errorMessage= "You are not authorized for the requested data.";
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("403");
        return mav;
    }


}
