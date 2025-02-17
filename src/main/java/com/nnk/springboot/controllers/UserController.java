package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.User;
import com.nnk.springboot.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/user/list")
    public String home(HttpServletRequest request, Model model)  {
        model.addAttribute("users", userService.loadAll());
        model.addAttribute("remoteUser", request.getRemoteUser());
        model.addAttribute("remoteRole", userService.getRemoteRole());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid  @ModelAttribute("user") User user, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {return "user/add";}

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userService.save(user);
        model.addAttribute("users", userService.loadAll());
        model.addAttribute("remoteUser", request.getRemoteUser());
        model.addAttribute("remoteRole", userService.getRemoteRole());
        return "/user/list";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<User> user = userService.load(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "user/update";
        }
        return "redirect:/user/list";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {return "user/update";}
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userService.save(user);
        model.addAttribute("users", userService.loadAll());
        model.addAttribute("remoteUser", request.getRemoteUser());
        model.addAttribute("remoteRole", userService.getRemoteRole());
        return "/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) {
        userService.delete(id);
        return "redirect:/user/list";
    }

}
