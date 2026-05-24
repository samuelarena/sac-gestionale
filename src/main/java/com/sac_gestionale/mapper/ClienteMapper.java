package com.sac_gestionale.mapper;

import com.sac_gestionale.dto.ClienteDettaglioDTO;
import com.sac_gestionale.dto.ClienteSummaryDTO;
import com.sac_gestionale.entity.Cliente;

public class ClienteMapper {

    public static ClienteSummaryDTO toSummaryDTO(Cliente cliente) {
        // Controllo di sicurezza: se il database non ci passa nulla, restituiamo nulla
        if (cliente == null) {
            return null;
        }

        // Logica di mascheramento del Codice Fiscale
        String cfOriginale = cliente.getCodiceFiscale();
        String cfMascherato;

        if (cfOriginale != null && cfOriginale.length() == 16) {
            // Prende i primi 6 caratteri, aggiunge 6 asterischi, e appiccica gli ultimi 4
            cfMascherato = cfOriginale.substring(0, 6) + "******" + cfOriginale.substring(12, 16);
        } else {
            // Se per qualche motivo il CF non è di 16 caratteri, lo nascondiamo del tutto per sicurezza
            cfMascherato = "***DATO NASCOSTO***";
        }

        // Costruiamo e restituiamo il nostro "guscio" sicuro (il DTO)
        return new ClienteSummaryDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCognome(),
                cfMascherato, // Attenzione: qui passiamo la stringa nascosta, non quella del DB!
                cliente.getDataNascita(),
                cliente.getLuogoNascita(),
                cliente.getEmail(),
                cliente.getTelefono(),
                cliente.getIndirizzoResidenza()
        );
    }

    public static ClienteDettaglioDTO toDettaglioDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        return new ClienteDettaglioDTO (
                cliente.getId(),
                cliente.getNome(),
                cliente.getCognome(),
                cliente.getCodiceFiscale(), // Attenzione: qui passiamo la stringa nascosta, non quella del DB!
                cliente.getDataNascita(),
                cliente.getLuogoNascita(),
                cliente.getEmail(),
                cliente.getTelefono(),
                cliente.getIndirizzoResidenza()
        );
    }
}