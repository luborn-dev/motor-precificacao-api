
package br.com.hacka.motorprecificacao.service

import br.com.hacka.motorprecificacao.dto.CnpjDTO
import br.com.hacka.motorprecificacao.dto.CnpjResponse
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

// Serviço responsável por consultar e validar CNPJs via API externa da ReceitaWS.

@Service
class CnpjService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()
    private val API_URL = "https://www.receitaws.com.br/v1/cnpj"

    fun consultarCnpj(cnpj: String): CnpjResponse {
        val cnpjLimpo = cnpj.replace(".", "").replace("/", "").replace("-", "").trim()

        if (!isValidCnpj(cnpjLimpo)) {
            throw IllegalArgumentException("CNPJ inválido: $cnpj. Deve conter 14 dígitos.")
        }

        try {
            val request = Request.Builder()
                .url("$API_URL/$cnpjLimpo")
                .addHeader("Accept", "application/json")
                .build()

            val empresa = client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw Exception("Erro ao consultar CNPJ: ${response.code}")
                }

                val body = response.body?.string() ?: throw Exception("Resposta vazia da API")

                // API retorna:
                // { "status": "ERROR" } quando não encontra
                if (body.contains("\"status\":\"ERROR\"")) {
                    throw Exception("CNPJ não encontrado: $cnpj")
                }

                val cnpjDTO = gson.fromJson(body, CnpjDTO::class.java)
                cnpjDTO.toResponse()
            }

            return empresa

        } catch (e: Exception) {
            if (e.message?.contains("CNPJ") == true) {
                throw e
            }
            throw Exception("Erro ao consultar CNPJ na API externa: ${e.message}")
        }
    }

    private fun isValidCnpj(cnpj: String): Boolean {
        return cnpj.matches(Regex("\\d{14}"))
    }

    private fun CnpjDTO.toResponse(): CnpjResponse {
        return CnpjResponse(
            cnpj = this.cnpj,
            nome = this.nome,
            fantasia = this.fantasia,
            telefone = this.telefone,
            email = this.email,
            situacao = this.situacao,
            logradouro = this.logradouro,
            numero = this.numero,
            municipio = this.municipio,
            uf = this.uf,
            cep = this.cep,
            atividade_principal = this.atividade_principal
        )
    }
}
 