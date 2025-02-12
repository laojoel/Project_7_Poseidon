package com.nnk.springboot.services;

import com.nnk.springboot.domains.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointService {
    @Autowired private CurvePointRepository curvePointRepository;

    public List<CurvePoint> loadAll() {return curvePointRepository.findAll();}
    public Optional<CurvePoint> load(int id) {return curvePointRepository.findById(id);}
    public void save(CurvePoint curvePoint) {curvePointRepository.save(curvePoint);}
    public void delete(int id){curvePointRepository.deleteById(id);}
}
