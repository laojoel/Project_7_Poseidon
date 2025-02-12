package com.nnk.springboot.services;

import com.nnk.springboot.domains.Bid;
import com.nnk.springboot.domains.Rule;
import com.nnk.springboot.repositories.BidRepository;
import com.nnk.springboot.repositories.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleService {
    @Autowired
    private RuleRepository ruleRepository;

    public List<Rule> loadAll() {return ruleRepository.findAll();}
    public Optional<Rule> load(int id) {return ruleRepository.findById(id);}
    public void save(Rule rule) {ruleRepository.save(rule);}
    public void delete(int id){ruleRepository.deleteById(id);}
}
