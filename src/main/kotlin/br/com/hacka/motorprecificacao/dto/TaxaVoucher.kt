package br.com.hacka.motorprecificacao.dto

/**
 * DTO que representa as informações associadas a vouchers de benefício.
 *
 * Inclui o tipo de voucher e uma descrição opcional.
 */
data class TaxaVoucher(
    /** Nome do voucher (ALELO, SODEXO, TICKET, etc) */
    val nome: String,
    /** Descrição ou observação sobre o voucher */
    val observacao: String? = null
)
