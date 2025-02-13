package com.nnk.springboot.services;

import com.nnk.springboot.domains.Curve;
import com.nnk.springboot.repositories.CurveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CurveService {

    @Autowired
    private CurveRepository curvePointRepository;

    public List<Curve> loadAll() {
        return curvePointRepository.findAll();
    }

    public Optional<Curve> load(int id) {
        return curvePointRepository.findById(id);

    }
    public void save(Curve curvePoint) {
        curvePointRepository.save(curvePoint);
    }

    public void delete(int id){
        curvePointRepository.deleteById(id);
    }
}
