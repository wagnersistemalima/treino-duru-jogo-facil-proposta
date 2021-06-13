package br.com.zup.wagner.proposta.novaProposta.controller;


import br.com.zup.wagner.proposta.apiClientes.consultarDadosSolicitante.ConsultarDadosFeingCliente;
import br.com.zup.wagner.proposta.exceptions.ExceptionGenericValidation;
import br.com.zup.wagner.proposta.novaProposta.model.Proposta;
import br.com.zup.wagner.proposta.novaProposta.repository.PropostaRepository;
import br.com.zup.wagner.proposta.novaProposta.request.NovaPropostaRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Validated
@RestController
@RequestMapping("propostas")
public class NovaPropostaController {

    // carga = 7

    private Logger logger = LoggerFactory.getLogger(NovaPropostaController.class); //1

    @Autowired
    private ConsultarDadosFeingCliente consultarDadosFeingCliente; //1

    @Autowired
    private PropostaRepository propostaRepository; //1

    // end point cadastrar proposta

    @Transactional
    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody NovaPropostaRequest request) { //1
        logger.info("...Iniciando cadastro de uma proposta...");

        // validação para proposta unica

        if(propostaRepository.existsByDocumento(request.getDocumento())) { //1
            throw new ExceptionGenericValidation("Solicitante com este documento já tem proposta cadastrada");
        }

        Proposta proposta = request.toModel(); //1
        propostaRepository.save(proposta);

        logger.info("...proposta salva com sucesso...");

        // consultar dados do solicitante, na api cliente feing

        proposta.enviaParaAnalise(consultarDadosFeingCliente);


        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(proposta.getId()).toUri(); //1

        return ResponseEntity.created(uri).build();
    }
}
