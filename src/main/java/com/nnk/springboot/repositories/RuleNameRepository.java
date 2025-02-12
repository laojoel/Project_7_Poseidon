package com.nnk.springboot.repositories;

import com.nnk.springboot.domains.Rating;
import com.nnk.springboot.domains.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RuleNameRepository extends JpaRepository<RuleName, Integer>, JpaSpecificationExecutor<RuleName> {

}
