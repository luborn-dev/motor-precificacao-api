package br.com.hacka.motorprecificacao

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
class CepControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `deve retornar 200 com endereco valido para CEP valido`() {
        mockMvc.perform(get("/api/cep/01310100"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.cep").value("01310-100"))
            .andExpect(jsonPath("$.rua").exists())
            .andExpect(jsonPath("$.cidade").exists())
            .andExpect(jsonPath("$.estado").value("SP"))
    }

    @Test
    fun `deve retornar 200 com CEP formatado com hifen`() {
        mockMvc.perform(get("/api/cep/01310-100"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.cep").value("01310-100"))
    }

    @Test
    fun `deve retornar 400 para CEP invalido - menos de 8 digitos`() {
        mockMvc.perform(get("/api/cep/0131010"))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `deve retornar 400 para CEP invalido - mais de 8 digitos`() {
        mockMvc.perform(get("/api/cep/013101000"))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `deve retornar 400 para CEP com caracteres invalidos`() {
        mockMvc.perform(get("/api/cep/0131010A"))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `deve retornar 404 para CEP nao encontrado`() {
        mockMvc.perform(get("/api/cep/99999999"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `deve retornar endereco correto do Rio de Janeiro`() {
        mockMvc.perform(get("/api/cep/20040020"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.cidade").value("Rio de Janeiro"))
            .andExpect(jsonPath("$.estado").value("RJ"))
            .andExpect(jsonPath("$.ddd").value("21"))
    }

    @Test
    fun `deve retornar endereco correto de Brasilia`() {
        mockMvc.perform(get("/api/cep/70040902"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.cidade").value("Brasília"))
            .andExpect(jsonPath("$.estado").value("DF"))
            .andExpect(jsonPath("$.ddd").value("61"))
    }
}

