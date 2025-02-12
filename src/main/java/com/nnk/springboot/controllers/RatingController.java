package com.nnk.springboot.controllers;

import com.nnk.springboot.domains.Bid;
import com.nnk.springboot.domains.Rating;
import com.nnk.springboot.services.RatingService;
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
public class RatingController {
    @Autowired RatingService ratingService;

    @RequestMapping("/rating/list")
    public String home(HttpServletRequest request, Model model)  {
        model.addAttribute("ratings", ratingService.loadAll());
        model.addAttribute("remoteUser", request.getRemoteUser());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        model.addAttribute("rating", new Rating());
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid @ModelAttribute("rating") Rating rating, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {return "rating/add";}
        ratingService.save(rating);
        model.addAttribute("ratings", ratingService.loadAll());
        model.addAttribute("remoteUser", request.getRemoteUser());
        return "rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Optional<Rating> rating = ratingService.load(id);
        if (rating.isPresent()) {
            model.addAttribute("rating", rating.get());
            return "rating/update";
        }
        return "redirect:/rating/list";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@Valid @ModelAttribute("rating") Rating rating, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {return "rating/update";}
        ratingService.save(rating);
        model.addAttribute("ratings", ratingService.loadAll());
        model.addAttribute("remoteUser", request.getRemoteUser());
        return "rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id) {
        ratingService.delete(id);
        return "redirect:/rating/list";
    }
}
