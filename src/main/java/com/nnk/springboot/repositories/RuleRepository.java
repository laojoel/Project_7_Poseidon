package com.nnk.springboot.repositories;

import com.nnk.springboot.domains.Bid;
import com.nnk.springboot.domains.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RuleRepository extends JpaRepository<Rule, Integer>, JpaSpecificationExecutor<Rule> {
}
