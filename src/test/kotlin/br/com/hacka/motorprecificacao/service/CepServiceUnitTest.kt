package br.com.hacka.motorprecificacao.service

import br.com.hacka.motorprecificacao.dto.EnderecoResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@DisplayName("Testes do CepService")
class CepServiceUnitTest {

    private lateinit var cepService: CepService

    @BeforeEach
    fun setUp() {
        cepService = CepService()
    }

    @Test
    @DisplayName("Deve retornar endereço válido para CEP válido")
    fun testConsultarCepValido() {
        // Arrange
        val cepValido = "01310100"

        // Act
        val resultado = cepService.consultarCep(cepValido)

        // Assert
        assertEquals("01310-100", resultado.cep)
        assertEquals("SP", resultado.estado)
        assertNotNull(resultado.rua)
        assertNotNull(resultado.cidade)
    }

    @Test
    @DisplayName("Deve limpar e processar CEP com hífen")
    fun testConsultarCepComHifen() {
        // Arrange
        val cepComHifen = "01310-100"

        // Act
        val resultado = cepService.consultarCep(cepComHifen)

        // Assert
        assertEquals("01310-100", resultado.cep)
        assertEquals("SP", resultado.estado)
    }

    @Test
    @DisplayName("Deve lançar exceção para CEP com menos de 8 dígitos")
    fun testConsultarCepComMenosDe8Digitos() {
        // Arrange
        val cepInvalido = "0131010"

        // Act & Assert
        val exception = assertThrows<IllegalArgumentException> {
            cepService.consultarCep(cepInvalido)
        }
        assertEquals("CEP inválido: 0131010. Deve conter 8 dígitos.", exception.message)
    }

    @Test
    @DisplayName("Deve lançar exceção para CEP com mais de 8 dígitos")
    fun testConsultarCepComMaisDe8Digitos() {
        // Arrange
        val cepInvalido = "013101000"

        // Act & Assert
        assertThrows<IllegalArgumentException> {
            cepService.consultarCep(cepInvalido)
        }
    }

    @Test
    @DisplayName("Deve lançar exceção para CEP com caracteres inválidos")
    fun testConsultarCepComCaracteresInvalidos() {
        // Arrange
        val cepInvalido = "0131010A"

        // Act & Assert
        assertThrows<IllegalArgumentException> {
            cepService.consultarCep(cepInvalido)
        }
    }

    @Test
    @DisplayName("Deve lançar exceção para CEP não encontrado na API")
    fun testConsultarCepNaoEncontrado() {
        // Arrange
        val cepInexistente = "99999999"

        // Act & Assert
        assertThrows<Exception> {
            cepService.consultarCep(cepInexistente)
        }
    }

    @Test
    @DisplayName("Deve retornar endereço correto do Rio de Janeiro")
    fun testConsultarCepRioDeJaneiro() {
        // Arrange
        val cepRJ = "20040020"

        // Act
        val resultado = cepService.consultarCep(cepRJ)

        // Assert
        assertEquals("RJ", resultado.estado)
        assertEquals("Rio de Janeiro", resultado.cidade)
        assertEquals("21", resultado.ddd)
    }

    @Test
    @DisplayName("Deve retornar endereço correto de Brasília")
    fun testConsultarCepBrasilia() {
        // Arrange
        val cepDF = "70040902"

        // Act
        val resultado = cepService.consultarCep(cepDF)

        // Assert
        assertEquals("DF", resultado.estado)
        assertEquals("Brasília", resultado.cidade)
        assertEquals("61", resultado.ddd)
    }

    @Test
    @DisplayName("Deve validar formato correto do CEP com regex")
    fun testValidarFormatoCepComRegex() {
        // Arrange
        val cepValido = "01310100"
        val cepInvalido = "0131010"

        // Act
        val resultadoValido = cepService.consultarCep(cepValido)

        // Assert
        assertEquals("01310-100", resultadoValido.cep)
        assertThrows<IllegalArgumentException> {
            cepService.consultarCep(cepInvalido)
        }
    }
}

