package br.senac.sp.tads.dsw.exemplo5.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import br.senac.sp.tads.dsw.exemplo5.model.Departamento;
import br.senac.sp.tads.dsw.exemplo5.model.Funcionario;
import br.senac.sp.tads.dsw.exemplo5.repository.DepartamentoRepository;
import br.senac.sp.tads.dsw.exemplo5.repository.FuncionarioRepository;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional 
public class FuncionarioControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Departamento departamentoPadrao;

    @BeforeEach 
    void setUp() {
        Departamento departamento = new Departamento();
        departamento.setNome("Departamento Padrão");
        departamento.setOrcamento(10000.00);
        departamentoPadrao = departamentoRepository.save(departamento);
    }

    @Test 
    void deveCriarFuncionarioComSucesso() throws Exception {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Eduardo");
        funcionario.setDataContratacao(LocalDate.now());
        funcionario.setTrabalhoRemoto(true);
        funcionario.setDepartamento(departamentoPadrao);

        String json = objectMapper.writeValueAsString(funcionario);

          mockMvc.perform(
                    post("/api/funcionarios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json)
                )
        // 4 - Checar se a resposta é o que foi enviado
                .andExpect(status().isCreated()) // 201
                .andExpect(jsonPath("$.id").exists()) // verifica se o ID existe
                .andExpect(jsonPath("$.nome").value("Turma B - TADS"))
                .andExpect(jsonPath("$.orcamento").value(3600.00)); 
    }

    @Test 
    void deveRetornarErro400AoCriarFuncionarioComDataFutura() throws Exception {

    }

    @Test 
    void deveListarTodosOsFuncionarios() throws Exception {

    }

    @Test
    void deveBuscarFuncionarioPorId() throws Exception {

    }

    @Test 
    void deveAtualizarFuncionario() throws Exception {

    }

    @Test 
    void deveApagarFuncionario() throws Exception {
        
    }
}
