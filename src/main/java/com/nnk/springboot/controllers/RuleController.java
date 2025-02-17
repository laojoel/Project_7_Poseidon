package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.Rule;
import com.nnk.springboot.services.RuleService;
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
public class RuleController {

    private RuleService ruleService;
    private UserService userService;

    @Autowired
    public void setRuleService(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/rule/list")
    public String home(HttpServletRequest request, Model model)  {
        model.addAttribute("rules", ruleService.loadAll());
        model.addAttribute("remoteUser", request.getRemoteUser());
        model.addAttribute("remoteRole", userService.getRemoteRole());
        return "rule/list";
    }

    @GetMapping("/rule/add")
    public String addBidForm(Model model) {
        model.addAttribute("rule", new Rule());
        return "rule/add";
    }

    @PostMapping("/rule/validate")
    public String validate(@Valid @ModelAttribute("rule") Rule rule, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {return "rule/add";}
        ruleService.save(rule);
        model.addAttribute("rules", ruleService.loadAll());
        model.addAttribute("remoteUser", request.getRemoteUser());
        model.addAttribute("remoteRole", userService.getRemoteRole());
        return "rule/list";
    }
    @GetMapping("/rule/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<Rule> rule = ruleService.load(id);
        if (rule.isPresent()) {
            model.addAttribute("rule", rule.get());
            return "rule/update";
        }
        return "redirect:/rule/list";
    }

    @PostMapping("/rule/update/{id}")
    public String updateRule(@Valid @ModelAttribute("rule") Rule rule, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {return "rule/update";}
        ruleService.save(rule);
        model.addAttribute("rules", ruleService.loadAll());
        model.addAttribute("remoteUser", request.getRemoteUser());
        model.addAttribute("remoteRole", userService.getRemoteRole());
        return "rule/list";
    }
    @GetMapping("/rule/delete/{id}")
    public String deleteRule(@PathVariable("id") Integer id) {
        ruleService.delete(id);
        return "redirect:/rule/list";
    }
}
