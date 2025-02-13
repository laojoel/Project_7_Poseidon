package com.nnk.springboot.services;

import com.nnk.springboot.domains.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public List<Rating> loadAll() {
        return ratingRepository.findAll();
    }

    public Optional<Rating> load(int id) {
        return ratingRepository.findById(id);
    }

    public void save(Rating rating) {
        ratingRepository.save(rating);
    }

    public void delete(int id){
        ratingRepository.deleteById(id);
    }
}