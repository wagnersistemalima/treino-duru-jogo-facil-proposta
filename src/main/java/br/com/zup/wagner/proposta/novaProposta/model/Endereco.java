package br.com.zup.wagner.proposta.novaProposta.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Embeddable                     // classe compositora
public class Endereco {

    // atributos basicos

    private String logradouro;

    private String bairro;

    private String complemento;

    private String uf;

    private String cep;

    // construtor default

    @Deprecated
    public Endereco() {
    }

    // construtor com atributos


    public Endereco(String logradouro, String bairro, String complemento, String uf, String cep) {
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.complemento = complemento;
        this.uf = uf;
        this.cep = cep;
    }

    // getters


    public String getLogradouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getUf() {
        return uf;
    }

    public String getCep() {
        return cep;
    }

}
