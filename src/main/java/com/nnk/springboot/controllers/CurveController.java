package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.Curve;
import com.nnk.springboot.services.CurveService;
import com.nnk.springboot.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class CurveController {

    private CurveService curveService;
    private UserService userService;

    @Autowired
    public void setCurveService(CurveService curveService) {
        this.curveService = curveService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/curve/list")
    public String home(HttpServletRequest request, Model model) {
        model.addAttribute("curves", curveService.loadAll());
        model.addAttribute("remoteUser", request.getRemoteUser());
        model.addAttribute("remoteRole", userService.getRemoteRole());
        return "curve/list";
    }

    @GetMapping("/curve/add")
    public String addCurveForm(Model model) {
        model.addAttribute("curve", new Curve());
        return "curve/add";
    }

    @PostMapping("/curve/validate")
    public String validate(@Valid @ModelAttribute("curve") Curve curve, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {return "curve/add";}
        curveService.save(curve);
        model.addAttribute("curves", curveService.loadAll());
        model.addAttribute("remoteUser", request.getRemoteUser());
        model.addAttribute("remoteRole", userService.getRemoteRole());
        return "curve/list";
    }

    @GetMapping("/curve/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<Curve> curve = curveService.load(id);
        if (curve.isPresent()) {
            model.addAttribute("curve", curve.get());
            return "curve/update";
        }
        return "redirect:/curve/list";
    }

    @PostMapping("/curve/update/{id}")
    public String updateCurve(@Valid @ModelAttribute("curve") Curve curve, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {return "curve/update";}
        curveService.save(curve);
        model.addAttribute("curves", curveService.loadAll());
        model.addAttribute("remoteUser", request.getRemoteUser());
        model.addAttribute("remoteRole", userService.getRemoteRole());
        return "curve/list";
    }

    @GetMapping("/curve/delete/{id}")
    public String deleteCurve(@PathVariable("id") Integer id) {
        curveService.delete(id);
        return "redirect:/curve/list";
    }
}
