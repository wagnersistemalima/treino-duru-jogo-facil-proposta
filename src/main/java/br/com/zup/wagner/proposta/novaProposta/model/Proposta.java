package br.com.zup.wagner.proposta.novaProposta.model;

import br.com.zup.wagner.proposta.apiClientes.consultarDadosSolicitante.ConsultarDadosFeingCliente;
import br.com.zup.wagner.proposta.apiClientes.consultarDadosSolicitante.ConsultarDadosRequest;
import br.com.zup.wagner.proposta.apiClientes.consultarDadosSolicitante.ConsultarDadosResponse;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // atributos basicos

    private String documento;                 // pode ser cpf ou cnpj

    private String email;

    private String nome;

    @Embedded                             // classe da composição
    private Endereco endereco;

    private BigDecimal salario;

    private String statusDaProposta = "NAO_ELEGIVEL";

    private LocalDateTime dataRegistro = LocalDateTime.now();

    private LocalDateTime updateDataRegistro;

    // construtor default

    @Deprecated
    public Proposta() {
    }

    // construtor com atributos


    public Proposta(String documento, String email, String nome, Endereco endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    // getters


    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public String getStatusDaProposta() {
        return statusDaProposta;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public LocalDateTime getUpdateDataRegistro() {
        return updateDataRegistro;
    }

    // equals & hashCode comparando pelo documento

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proposta proposta = (Proposta) o;
        return Objects.equals(getDocumento(), proposta.getDocumento());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDocumento());
    }


    // metodo para persistir no banco o instante da atualização da proposta

    @PreUpdate
    public void preUpdate() {
        updateDataRegistro = LocalDateTime.now();
    }

    // metodo para enviar a proposta para a analize

    public void enviaParaAnalise(ConsultarDadosFeingCliente consultarDadosFeingCliente) {

        ConsultarDadosRequest request = new ConsultarDadosRequest(documento, nome, id.toString());

        ConsultarDadosResponse response = consultarDadosFeingCliente.consultarAnalise(request);

        if (response.getResultadoSolicitacao().equals("SEM_RESTRICAO")) {
            this.statusDaProposta = response.getResultadoSolicitacao();
        }
        else {
            this.statusDaProposta = response.getResultadoSolicitacao();
        }

        preUpdate();
    }
}
