package com.sac_gestionale.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sac_gestionale.entity.Cliente;
import com.sac_gestionale.entity.Documento;
import com.sac_gestionale.repository.ClienteRepository;
import com.sac_gestionale.repository.DocumentoRepository;

@Service
public class DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final ClienteRepository clienteRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public DocumentoService(DocumentoRepository documentoRepository, ClienteRepository clienteRepository) {
        this.documentoRepository = documentoRepository;
        this.clienteRepository = clienteRepository;
    }

    public Documento salvaDocumento(Integer clienteId, MultipartFile file) throws IOException {
        // Trova il Cliente
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente non trovato con ID: " + clienteId));

        // Prepara la cartella
        String cartellaBase = uploadDir + "/clienti/" + clienteId;
        Path uploadPath = Paths.get(cartellaBase);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Salva il file fisico
        String nomeOriginale = file.getOriginalFilename();
        if (nomeOriginale == null) {
            nomeOriginale = "documento_sconosciuto";
        }
        
        Path percorsoFinale = uploadPath.resolve(nomeOriginale);
        
        // Scrive fisicamente i byte sul disco (sostituendo eventuali file esistenti con lo stesso nome)
        Files.copy(file.getInputStream(), percorsoFinale, StandardCopyOption.REPLACE_EXISTING);

        // Salva nel Database
        Documento documento = new Documento();
        documento.setNomeFile(nomeOriginale);
        documento.setTipo(file.getContentType());
        documento.setPercorsoFisico(percorsoFinale.toAbsolutePath().toString());
        documento.setDataCaricamento(LocalDate.now());
        documento.setCliente(cliente);

        return documentoRepository.save(documento);
    }

    // Aggiungi questo metodo per recuperare l'entità dal DB
    public Documento getDocumento(Long id) {
        return documentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento non trovato nel database con ID: " + id));
    }

    // Aggiungi questo metodo per recuperare il file fisico dal disco
    public org.springframework.core.io.Resource caricaDocumentoComeRisorsa(Long id) throws Exception {
        //  Trova il documento nel DB
        Documento doc = getDocumento(id);

        //  Costruisci il percorso fisico
        Path percorsoFisico = Paths.get(doc.getPercorsoFisico());

        //  Trasforma il percorso in una "Risorsa" scaricabile
        org.springframework.core.io.Resource risorsa = new org.springframework.core.io.UrlResource(percorsoFisico.toUri());

        // Controlla se il file esiste davvero sul disco e se è leggibile
        if (risorsa.exists() || risorsa.isReadable()) {
            return risorsa;
        } else {
            throw new RuntimeException("Impossibile leggere il file o file non trovato sul disco fisico!");
        }
    }
}
