package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.Bid;
import com.nnk.springboot.services.BidService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
public class BidController {
    private BidService bidService;
    @Autowired
    public void setBidService(BidService bidService) {
        this.bidService = bidService;
    }

    @RequestMapping("/bid/list")
    public String home(HttpServletRequest request, Model model)  {
        model.addAttribute("bids", bidService.loadAll());
        model.addAttribute("remoteUser", request.getRemoteUser());
        return "bid/list";
    }

    @GetMapping("/bid/add")
    public String addBidForm(Model model) {
        model.addAttribute("bid", new Bid());
        return "bid/add";
    }
    @PostMapping("/bid/validate")
    public String validate(@Valid @ModelAttribute("bid") Bid bid, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {return "bid/add";}
        bidService.save(bid);
        model.addAttribute("bids", bidService.loadAll());
        model.addAttribute("remoteUser", request.getRemoteUser());
        return "bid/list";
    }
    @GetMapping("/bid/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<Bid> bid = bidService.load(id);
        if (bid.isPresent()) {
            model.addAttribute("bid", bid.get());
            return "bid/update";
        }
        return "redirect:/bid/list";
    }

    @PostMapping("/bid/update/{id}")
    public String updateBid(@Valid @ModelAttribute("bid") Bid bid, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {return "bid/update";}
        bidService.save(bid);
        model.addAttribute("bids", bidService.loadAll());
        model.addAttribute("remoteUser", request.getRemoteUser());
        return "bid/list";
    }

    @GetMapping("/bid/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) {
        bidService.delete(id);
        return "redirect:/bid/list";
    }
}
