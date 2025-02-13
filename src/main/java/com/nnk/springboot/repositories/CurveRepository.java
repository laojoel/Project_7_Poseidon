package com.nnk.springboot.repositories;

import com.nnk.springboot.domains.Curve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CurveRepository extends JpaRepository<Curve, Integer>, JpaSpecificationExecutor<Curve> {

}
