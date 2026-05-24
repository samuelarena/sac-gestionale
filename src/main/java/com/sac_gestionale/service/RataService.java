package com.sac_gestionale.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sac_gestionale.entity.Rata;
import com.sac_gestionale.repository.RataRepository;

@Service
public class RataService {

    @Autowired
    private RataRepository rataRepository;

    public List<Rata> getInsoluti() {
        return rataRepository.findInsoluti(LocalDate.now());
    }
}