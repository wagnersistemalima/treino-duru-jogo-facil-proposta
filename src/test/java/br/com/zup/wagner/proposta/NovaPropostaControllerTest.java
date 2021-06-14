package br.com.zup.wagner.proposta;

import br.com.zup.wagner.proposta.apiClientes.consultarDadosSolicitante.ConsultarDadosFeingCliente;
import br.com.zup.wagner.proposta.apiClientes.consultarDadosSolicitante.ConsultarDadosResponse;
import br.com.zup.wagner.proposta.novaProposta.model.Endereco;
import br.com.zup.wagner.proposta.novaProposta.model.Proposta;
import br.com.zup.wagner.proposta.novaProposta.repository.PropostaRepository;
import br.com.zup.wagner.proposta.novaProposta.request.EnderecoRequest;
import br.com.zup.wagner.proposta.novaProposta.request.NovaPropostaRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@AutoConfigureDataJpa
public class NovaPropostaControllerTest {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean                        // mock da api externa
    ConsultarDadosFeingCliente consultarDadosFeingCliente;


    private BigDecimal salario = new BigDecimal(1000.0);
    private String documento = "04394450438";
    private String nome = "nomeTeste";
    private String email = "nome@gmail.com";

    private String logradouro = "Rua das flores";
    private String bairro = "Bodocongo";
    private String complemento = "perto da padaria";
    private String uf = "PB";
    private String cep = "58410505";

    private String resultadoSolicitacao = "SEM_RESTRICAO";
    private String idProposta = "1";

    //------------------------------------
    private String documentoInvalido = "0439445042";
    private BigDecimal salarioNegativo = new BigDecimal(-1000.0);
    private String emailInvalido = "testeemail.com";

    // 1 cenario de test / caminho feliz

    @Test
    @DisplayName("deve retornar 200, cadastrar uma proposta")
    public void deveRetornar200() throws Exception {

        // cenario

        Mockito.when(consultarDadosFeingCliente.consultarAnalise(Mockito.any())).thenReturn(new ConsultarDadosResponse(documento, nome, resultadoSolicitacao, idProposta));

        EnderecoRequest endereco = new EnderecoRequest(logradouro, bairro, complemento, uf, cep);

        NovaPropostaRequest request = new NovaPropostaRequest(documento, email, nome, endereco, salario);

        URI uri = new URI("/propostas");

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(201));


        //assertivas
    }

    // 2 cenario de test /

    @Test
    @DisplayName("deve retornar exception 422 quando documento ja existe pra esta proposta")
    public void deveRetornar422QuandoDocumentoExisteParaAProposta() throws Exception {

        // cenario

        Endereco enderecoEntity = new Endereco(logradouro, bairro, complemento, uf, cep);

        Proposta proposta = new Proposta(documento, email, nome, enderecoEntity, salario);
        propostaRepository.save(proposta);

        Mockito.when(consultarDadosFeingCliente.consultarAnalise(Mockito.any())).thenReturn(new ConsultarDadosResponse(documento, nome, resultadoSolicitacao, proposta.getId().toString()));

        EnderecoRequest enderecoRequest = new EnderecoRequest(logradouro, bairro, complemento, uf, cep);

        NovaPropostaRequest request = new NovaPropostaRequest(documento, email, nome, enderecoRequest, salario);

        URI uri = new URI("/propostas");

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(422));

        //assertivas
    }

    // 3 cenario de test /

    @Test
    @DisplayName("deve retornar 400, erro de validação quando documento for invalido")
    public void deveRetornar400QuandoDocumentoInvalido() throws Exception {

        // cenario

        EnderecoRequest enderecoRequest = new EnderecoRequest(logradouro, bairro, complemento, uf, cep);

        NovaPropostaRequest request = new NovaPropostaRequest(documentoInvalido, email, nome, enderecoRequest, salario);

        URI uri = new URI("/propostas");

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(400));

        //assertivas
    }

    // 4 cenario de test /

    @Test
    @DisplayName("deve retornar 400, erro de validação quando salario for negativo")
    public void deveRetornar400QuandoSalarioInvalido() throws Exception {

        // cenario

        EnderecoRequest enderecoRequest = new EnderecoRequest(logradouro, bairro, complemento, uf, cep);

        NovaPropostaRequest request = new NovaPropostaRequest(documento, email, nome, enderecoRequest, salarioNegativo);

        URI uri = new URI("/propostas");

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(400));

        //assertivas
    }

    // 5 cenario de test /

    @Test
    @DisplayName("deve retornar 400, erro de validação quando email for invalido")
    public void deveRetornar400QuandoEmailInvalido() throws Exception {

        // cenario

        EnderecoRequest enderecoRequest = new EnderecoRequest(logradouro, bairro, complemento, uf, cep);

        NovaPropostaRequest request = new NovaPropostaRequest(documento, emailInvalido, nome, enderecoRequest, salario);

        URI uri = new URI("/propostas");

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(400));

        //assertivas
    }

    // 6 cenario de test /

    @Test
    @DisplayName("deve retornar 400, erro de validação quando endereco nullo")
    public void deveRetornar400QuandoEnderecoNullo() throws Exception {

        // cenario


        NovaPropostaRequest request = new NovaPropostaRequest(documento, emailInvalido, nome, null, salario);

        URI uri = new URI("/propostas");

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().is(400));

        //assertivas
    }



    // metodo para desserializar objeto da requisição

    private String toJson(NovaPropostaRequest request) throws JsonProcessingException {
        return  objectMapper.writeValueAsString(request);
    }

}
