package com.nnk.springboot.services;

import com.nnk.springboot.domains.Bid;
import com.nnk.springboot.repositories.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import javax.validation.Valid;
//import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BidService {
    @Autowired private BidRepository bidRepository;

    public List<Bid> loadAll() {return bidRepository.findAll();}
    public Optional<Bid> load(int id) {return bidRepository.findById(id);}
    public void save(Bid bid) {bidRepository.save(bid);}
    public void delete(int id){bidRepository.deleteById(id);}
}
