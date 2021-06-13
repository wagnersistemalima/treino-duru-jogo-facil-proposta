package br.com.zup.wagner.proposta.apiClientes.consultarDadosSolicitante;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ConsultarDadosRequest {

    // atributos basicos

    private String documento;

    private String nome;

    private String idProposta;

    // construtor com atributos

    @JsonCreator
    public ConsultarDadosRequest(String documento, String nome, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    // getters


    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
