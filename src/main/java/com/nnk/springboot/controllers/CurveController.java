package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.Bid;
import com.nnk.springboot.domains.CurvePoint;
import com.nnk.springboot.services.BidService;
import com.nnk.springboot.services.CurvePointService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//import javax.validation.Valid;

@Controller
public class CurveController {
    @Autowired private CurvePointService curvePointService;

    @RequestMapping("/curvePoint/list")
    public String home(HttpServletRequest request, Model model) {
        model.addAttribute("curvePoints", curvePointService.loadAll());
        model.addAttribute("remoteUser", request.getRemoteUser());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(Model model) {
        model.addAttribute("curvePoint", new CurvePoint());
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid @ModelAttribute("curvePoint") CurvePoint curvePoint, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {return "curvePoint/add";}
        curvePointService.save(curvePoint);
        model.addAttribute("curvePoints", curvePointService.loadAll());
        model.addAttribute("remoteUser", request.getRemoteUser());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<CurvePoint> curvePoint = curvePointService.load(id);
        if (curvePoint.isPresent()) {
            model.addAttribute("curvePoint", curvePoint.get());
            return "curvePoint/update";
        }
        return "redirect:/curvePoint/list";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurvePoint(@Valid @ModelAttribute("curvePoint") CurvePoint curvePoint, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {return "curvePoint/update";}
        curvePointService.save(curvePoint);
        model.addAttribute("curvePoints", curvePointService.loadAll());
        model.addAttribute("remoteUser", request.getRemoteUser());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurvePoint(@PathVariable("id") Integer id) {
        curvePointService.delete(id);
        return "redirect:/curvePoint/list";
    }
}
