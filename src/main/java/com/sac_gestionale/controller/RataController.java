package com.sac_gestionale.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sac_gestionale.entity.Rata;
import com.sac_gestionale.service.RataService;

@RestController
@RequestMapping("/api/rate")
public class RataController {

    @Autowired
    private RataService rataService;

    @GetMapping("/insoluti")
    public List<Rata> getInsoluti() {
        return rataService.getInsoluti();
    }
}