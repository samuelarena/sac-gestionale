package com.sac_gestionale.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.HashMap;

import com.sac_gestionale.service.PolizzaService;
import com.sac_gestionale.service.SinistroService;

@RestController
@RequestMapping("/api/statistiche")
public class StatisticheController {

    @Autowired
    private PolizzaService polizzaService;
    @Autowired
    private SinistroService sinistroService;

    @GetMapping("/riepilogo")
    public Map<String, Object> getDatiRiepilogo() {

        Map<String, Object> riepilogo = new HashMap<>();
        riepilogo.put("totalePolizze", polizzaService.contaPolizze());
        riepilogo.put("totaleIncassi", polizzaService.calcolaTotaleIncassi());
        riepilogo.put("totaleSinistri", sinistroService.contaSinistri());

        return riepilogo;
    }
    

}
