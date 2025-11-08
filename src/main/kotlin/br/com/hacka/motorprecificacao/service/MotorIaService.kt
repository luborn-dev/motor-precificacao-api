package br.com.hacka.motorprecificacao.service

import br.com.hacka.motorprecificacao.dto.MotorDTO
import br.com.hacka.motorprecificacao.dto.MotorResponse
import br.com.hacka.motorprecificacao.dto.TaxaBandeiraCredito
import br.com.hacka.motorprecificacao.dto.TaxaBandeiraDebito
import br.com.hacka.motorprecificacao.dto.TaxaVoucher
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

// Serviço responsável por consultar (ou simular) as taxas calculadas pelo motor de IA.

@Service
class MotorIaService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()
    private val API_URL = "http://motor-ia/taxas" // URL da API de IA

    fun consultarTaxasIA(request: MotorDTO): MotorResponse {
        try {
            // TODO Estamos mockando retorno enquanto modelo não está pronto

            return MotorResponse(
                debito = listOf(
                    TaxaBandeiraDebito(bandeira = "VISA", taxa_a_vista = 2.39),
                    TaxaBandeiraDebito(bandeira = "MASTERCARD", taxa_a_vista = 2.49),
                    TaxaBandeiraDebito(bandeira = "ELO", taxa_a_vista = 2.59)
                ),
                credito = listOf(
                    TaxaBandeiraCredito(bandeira = "VISA", taxa_a_vista = 3.39, taxa_parcelado_2a18x = 5.29),
                    TaxaBandeiraCredito(
                        bandeira = "MASTERCARD",
                        taxa_a_vista = 3.49,
                        taxa_parcelado_2a18x = 5.49
                    ),
                    TaxaBandeiraCredito(bandeira = "ELO", taxa_a_vista = 3.59, taxa_parcelado_2a18x = 5.69)
                ),
                voucher = listOf(
                    TaxaVoucher(nome = "ALELO", observacao = "Vale refeição e alimentação"),
                    TaxaVoucher(nome = "SODEXO", observacao = "Vale alimentação"),
                    TaxaVoucher(nome = "TICKET", observacao = "Vale refeição")
                )
            )

        } catch (e: Exception) {
            throw Exception("Erro ao consultar taxas da IA: ${e.message}")
        }
    }
}
