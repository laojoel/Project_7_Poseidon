package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.Trade;
import com.nnk.springboot.services.TradeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
public class TradeController {

    TradeService tradeService;
    @Autowired
    public void setTradeService(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @RequestMapping("/trade/list")
    public String home(HttpServletRequest request, Model model)  {
        model.addAttribute("trades", tradeService.loadAll());
        model.addAttribute("remoteUser", request.getRemoteUser());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTradeForm(Model model) {
        model.addAttribute("trade", new Trade());
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid @ModelAttribute("trade") Trade trade, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {return "trade/add";}
        tradeService.save(trade);
        model.addAttribute("trades", tradeService.loadAll());
        model.addAttribute("remoteUser", request.getRemoteUser());
        return "trade/list";
    }
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<Trade> trade = tradeService.load(id);
        if (trade.isPresent()) {
            model.addAttribute("trade", trade.get());
            return "trade/update";
        }
        return "redirect:/trade/list";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@Valid @ModelAttribute("trade") Trade trade, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {return "trade/update";}
        tradeService.save(trade);
        model.addAttribute("trades", tradeService.loadAll());
        model.addAttribute("remoteUser", request.getRemoteUser());
        return "trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id) {
        tradeService.delete(id);
        return "redirect:/trade/list";
    }
}
