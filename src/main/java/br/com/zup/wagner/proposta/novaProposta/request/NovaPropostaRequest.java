package br.com.zup.wagner.proposta.novaProposta.request;

import br.com.zup.wagner.proposta.novaProposta.model.Proposta;
import br.com.zup.wagner.proposta.novaProposta.validator.CpfOuCnpj;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class NovaPropostaRequest {

    // atributos basicos

    @CpfOuCnpj(domainClass = Proposta.class, fieldName = "documento")
    @NotBlank
    private String documento;                 // pode ser cpf ou cnpj

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String nome;

    @NotNull
    @Valid
    private EnderecoRequest endereco;

    @NotNull
    @Positive
    private BigDecimal salario;


    // construtor com atributos

    @JsonCreator
    public NovaPropostaRequest(String documento, String email, String nome, EnderecoRequest endereco, BigDecimal salario) {
        this.documento = documento.replaceAll("[^0-9]", "");// retira os caracteres . - / do documento
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    // getters


    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public EnderecoRequest getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    @Override
    public String toString() {
        return "NovaPropostaRequest{" +
                "documento='" + documento + '\'' +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", endereco=" + endereco +
                ", salario=" + salario +
                '}';
    }

    // metodo para converter a requisição em entidade

    public Proposta toModel() {

        return  new Proposta(documento, email, nome, endereco.toModel(), salario);
    }
}
