package br.com.zup.wagner.proposta.novaProposta.request;

import br.com.zup.wagner.proposta.novaProposta.model.Endereco;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Locale;

public class EnderecoRequest {

    // atributos basicos

    @NotBlank
    private String logradouro;

    @NotBlank
    private String bairro;

    @NotBlank
    private String complemento;

    @NotBlank
    @Size(max = 2)
    private String uf;

    @NotBlank
    private String cep;

    // construtor com atributos

    @JsonCreator
    public EnderecoRequest(String logradouro, String bairro, String complemento, String uf, String cep) {
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.complemento = complemento;
        this.uf = uf.toUpperCase(Locale.ROOT);
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

    @Override
    public String toString() {
        return "EnderecoRequest{" +
                "logradouro='" + logradouro + '\'' +
                ", bairro='" + bairro + '\'' +
                ", complemento='" + complemento + '\'' +
                ", uf='" + uf + '\'' +
                ", cep='" + cep + '\'' +
                '}';
    }

    //

    public Endereco toModel() {
        return new Endereco(logradouro, bairro, complemento, uf, cep);
    }
}
