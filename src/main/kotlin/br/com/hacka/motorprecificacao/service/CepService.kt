package br.com.hacka.motorprecificacao.service

import br.com.hacka.motorprecificacao.dto.EnderecoDTO
import br.com.hacka.motorprecificacao.dto.EnderecoResponse
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class CepService() {

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()
    private val API_URL = "https://viacep.com.br/ws"

    fun consultarCep(cep: String): EnderecoResponse {
        val cepLimpo = cep.replace("-", "").replace(" ", "")

        if (!isValidCep(cepLimpo)) {
            throw IllegalArgumentException("CEP inválido: $cep. Deve conter 8 dígitos.")
        }

        try {
            val request = Request.Builder()
                .url("$API_URL/$cepLimpo/json")
                .build()

            val endereco = client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw Exception("Erro ao consultar CEP: ${response.code}")
                }

                val body = response.body?.string() ?: throw Exception("Resposta vazia da API")

                // Verifica se o CEP foi encontrado
                if (body.contains("\"erro\"") || body.contains("erro")) {
                    throw Exception("CEP não encontrado: $cep")
                }

                val enderecoDTO = gson.fromJson(body, EnderecoDTO::class.java)
                enderecoDTO.toResponse()
            }

            return endereco
        } catch (e: Exception) {
            if (e.message?.contains("CEP") == true) {
                throw e
            }
            throw Exception("Erro ao consultar CEP na API externa: ${e.message}")
        }
    }

    private fun isValidCep(cep: String): Boolean {
        return cep.matches(Regex("\\d{8}"))
    }

    private fun EnderecoDTO.toResponse(): EnderecoResponse {
        return EnderecoResponse(
            cep = this.cep,
            rua = this.rua,
            complemento = this.complemento,
            bairro = this.bairro,
            cidade = this.cidade,
            estado = this.estado,
            ddd = this.ddd
        )
    }
}

