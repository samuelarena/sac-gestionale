package com.sac_gestionale.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import com.sac_gestionale.entity.Documento;
import com.sac_gestionale.service.DocumentoService;

@RestController
@RequestMapping("/api/documenti")
public class DocumentoController {

    private final DocumentoService documentoService;

    public DocumentoController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @PostMapping("/upload/{clienteId}")
    public ResponseEntity<?> uploadDocumento(
            @PathVariable Integer clienteId,
            @RequestParam("file") MultipartFile file) {
        try {
            Documento salvato = documentoService.salvaDocumento(clienteId, file);
            return ResponseEntity.ok(salvato);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante il caricamento del file: " + e.getMessage());
        }
    }

    @PostMapping("/sinistri/{sinistroId}")
    public ResponseEntity<?> uploadDocumentoSinistro(
            @PathVariable Integer sinistroId,
            @RequestParam("file") MultipartFile file) {
        try {
            Documento salvato = documentoService.salvaDocumentoSinistro(sinistroId, file);
            return ResponseEntity.ok(salvato);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante il caricamento del file: " + e.getMessage());
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadDocumento(@PathVariable Long id) {
        try {
            // Recuperiamo i dati del DB per sapere come si chiama il file originale
            Documento doc = documentoService.getDocumento(id);
            
            // Recuperiamo i byte del file
            Resource risorsa = documentoService.caricaDocumentoComeRisorsa(id);

            return ResponseEntity.ok()
                    //Impostiamo il Content-Type (es. application/pdf)
                    .contentType(MediaType.parseMediaType(doc.getTipo()))
                    
                    // Diciamo al browser di scaricarlo come allegato (inline)
                    // e gli diamo il suo nome originale
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + doc.getNomeFile() + "\"")
                    
                    .body(risorsa);
                    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 se qualcosa va storto
        }
    }
}
