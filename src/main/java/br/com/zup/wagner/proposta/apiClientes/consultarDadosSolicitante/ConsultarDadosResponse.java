package br.com.zup.wagner.proposta.apiClientes.consultarDadosSolicitante;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ConsultarDadosResponse {

    // atributos basicos

    private String documento;

    private String nome;

    private String resultadoSolicitacao;

    private String idProposta;

    // construtor com atributos

    @JsonCreator
    public ConsultarDadosResponse(String documento, String nome, String resultadoSolicitacao, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
        this.idProposta = idProposta;
    }

    // getters


    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
