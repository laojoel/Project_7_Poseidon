package com.nnk.springboot.repositories;

import com.nnk.springboot.domains.CurvePoint;
import com.nnk.springboot.domains.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RatingRepository extends JpaRepository<Rating, Integer>, JpaSpecificationExecutor<Rating> {

}
