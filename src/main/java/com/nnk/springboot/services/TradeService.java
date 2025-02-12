package com.nnk.springboot.services;

import com.nnk.springboot.domains.Rule;
import com.nnk.springboot.domains.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {
    @Autowired
    TradeRepository tradeRepository;

    public List<Trade> loadAll() {return tradeRepository.findAll();}
    public Optional<Trade> load(int id) {return tradeRepository.findById(id);}
    public void save(Trade trade) {tradeRepository.save(trade);}
    public void delete(int id){tradeRepository.deleteById(id);}
}
