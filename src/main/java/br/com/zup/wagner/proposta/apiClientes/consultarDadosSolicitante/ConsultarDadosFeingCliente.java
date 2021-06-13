package br.com.zup.wagner.proposta.apiClientes.consultarDadosSolicitante;

// definir o cliente web service

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Component
@FeignClient(name = "analize-cartoes", url = "http://localhost:9999")
public interface ConsultarDadosFeingCliente {

    // end point

    @RequestMapping(method = RequestMethod.POST, value = "/api/solicitacao")
    public ConsultarDadosResponse consultarAnalise(@RequestBody ConsultarDadosRequest request);
}
