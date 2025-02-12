package com.nnk.springboot.repositories;

import com.nnk.springboot.domains.Bid;
import com.nnk.springboot.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.ArrayList;

public interface BidRepository extends JpaRepository<Bid, Integer>, JpaSpecificationExecutor<Bid> {

}
