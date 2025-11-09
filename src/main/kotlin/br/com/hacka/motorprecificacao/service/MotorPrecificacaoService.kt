package br.com.hacka.motorprecificacao.service
import br.com.hacka.motorprecificacao.dto.MotorDTO
import br.com.hacka.motorprecificacao.dto.MotorResponse
import br.com.hacka.motorprecificacao.dto.TaxaBandeiraCredito
import br.com.hacka.motorprecificacao.dto.TaxaBandeiraDebito
import br.com.hacka.motorprecificacao.dto.TaxaVoucher
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit
/**
 * Serviço responsável por consultar as taxas calculadas pelo motor de IA.
 *
 * Atualmente realiza simulação de taxas conforme especificação de negócio.
 *
 * TODO: Integrar com API real do motor de IA quando modelo estiver pronto.
 * Endpoint esperado: POST {motor-ia.url}/taxas
 */
@Service
class MotorPrecificacaoService(
    @Value("\${motor-ia.api.url:http://motor-ia/taxas}")
    private val apiUrl: String,
    @Value("\${motor-ia.api.timeout:10}")
    private val timeoutSeconds: Long
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val client = OkHttpClient.Builder()
        .connectTimeout(timeoutSeconds, TimeUnit.SECONDS)
        .readTimeout(timeoutSeconds, TimeUnit.SECONDS)
        .writeTimeout(timeoutSeconds, TimeUnit.SECONDS)
        .build()
    private val gson = Gson()
    /**
     * Consulta as taxas de processamento baseado no perfil da empresa.
     *
     * @param request MotorDTO contendo dados da empresa e localização
     * @return MotorResponse com as taxas de débito, crédito e voucher
     * @throws Exception em caso de erro no processamento
     */
    fun consultarTaxasIA(request: MotorDTO): MotorResponse {
        logger.info("Consultando taxas IA para empresa em ${request.endereco.cidade}")
        logger.debug("Dados da requisição: faturamento=${request.faturamentoMensal}, atividades=${request.atividadePrincipal.size}")


        return try {
            // NOTA: Mockando retorno enquanto modelo de IA não está pronto
            // Quando integrado, será necessário:
            // 1. Serializar request para JSON
            // 2. Fazer POST para $apiUrl
            // 3. Desserializar resposta em MotorResponse
            val response = gerarTaxasSimuladas(request)
            logger.info("Taxas consultadas com sucesso para ${request.endereco.cidade}")
            response
        } catch (e: Exception) {
            logger.error("Erro ao consultar taxas da IA: ${e.message}", e)
            throw Exception("Erro ao consultar taxas do motor de IA: ${e.message}", e)
        }
    }
    /**
     * Gera simulação de taxas conforme especificação de negócio.
     *
     * Retorna taxas padrão por bandeira:
     * - Débito: VISA 2.39%, MASTERCARD 2.49%, ELO 2.59%
     * - Crédito à vista: VISA 3.39%, MASTERCARD 3.49%, ELO 3.59%
     * - Crédito parcelado (2-18x): VISA 5.29%, MASTERCARD 5.49%, ELO 5.69%
     * - Voucher: ALELO, SODEXO, TICKET
     */
    private fun gerarTaxasSimuladas(request: MotorDTO): MotorResponse {
        logger.debug("Gerando taxas simuladas")
        return MotorResponse(
            debito = listOf(
                TaxaBandeiraDebito(bandeira = "VISA", taxa_a_vista = 2.39),
                TaxaBandeiraDebito(bandeira = "MASTERCARD", taxa_a_vista = 2.49),
                TaxaBandeiraDebito(bandeira = "ELO", taxa_a_vista = 2.59)
            ),
            credito = listOf(
                TaxaBandeiraCredito(
                    bandeira = "VISA",
                    taxa_a_vista = 3.39,
                    taxa_parcelado_2a18x = 5.29
                ),
                TaxaBandeiraCredito(
                    bandeira = "MASTERCARD",
                    taxa_a_vista = 3.49,
                    taxa_parcelado_2a18x = 5.49
                ),
                TaxaBandeiraCredito(
                    bandeira = "ELO",
                    taxa_a_vista = 3.59,
                    taxa_parcelado_2a18x = 5.69
                )
            ),
            voucher = listOf(
                TaxaVoucher(nome = "ALELO", observacao = "Vale refeição e alimentação"),
                TaxaVoucher(nome = "SODEXO", observacao = "Vale alimentação"),
                TaxaVoucher(nome = "TICKET", observacao = "Vale refeição")
            )
        )
    }
}