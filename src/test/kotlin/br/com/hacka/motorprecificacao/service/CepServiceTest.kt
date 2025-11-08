package br.com.hacka.motorprecificacao.service

import br.com.hacka.motorprecificacao.exception.InvalidCepException
import br.com.hacka.motorprecificacao.exception.CepNotFoundException
import br.com.hacka.motorprecificacao.exception.ExternalApiException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class CepServiceTest {

    private lateinit var cepService: CepService

    @BeforeEach
    fun setUp() {
        cepService = CepService(
            apiUrl = "https://viacep.com.br/ws",
            timeoutSeconds = 10
        )
    }

    @Test
    fun `deve retornar endereco valido para CEP valido`() {
        // Arrange
        val cepValido = "01310100"

        // Act
        val resultado = cepService.consultarCep(cepValido)

        // Assert
        assertEquals("01310-100", resultado.cep)
        assertEquals("Avenida Paulista", resultado.rua)
        assertEquals("São Paulo", resultado.cidade)
        assertEquals("SP", resultado.estado)
    }

    @Test
    fun `deve limpar hifens e espacos do CEP`() {
        // Arrange
        val cepComHifen = "01310-100"

        // Act
        val resultado = cepService.consultarCep(cepComHifen)

        // Assert
        assertEquals("01310-100", resultado.cep)
    }

    @Test
    fun `deve lancar excecao para CEP com menos de 8 digitos`() {
        // Arrange
        val cepInvalido = "0131010"

        // Act & Assert
        assertThrows<InvalidCepException> {
            cepService.consultarCep(cepInvalido)
        }
    }

    @Test
    fun `deve lancar excecao para CEP com caracteres invalidos`() {
        // Arrange
        val cepInvalido = "0131010A"

        // Act & Assert
        assertThrows<InvalidCepException> {
            cepService.consultarCep(cepInvalido)
        }
    }

    @Test
    fun `deve lancar excecao para CEP nao encontrado`() {
        // Arrange
        val cepInexistente = "99999999"

        // Act & Assert
        // Pode lançar CepNotFoundException ou ExternalApiException conforme a resposta da API
        assertThrows<Exception> {
            cepService.consultarCep(cepInexistente)
        }
    }
}

