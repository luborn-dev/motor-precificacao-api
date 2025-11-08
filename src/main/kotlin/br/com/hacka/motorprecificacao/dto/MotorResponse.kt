package br.com.hacka.motorprecificacao.dto

/**
 * DTO de resposta que contém as listas de taxas de débito, crédito e voucher.
 *
 * Retornado pelo endpoint POST /api/motor/taxas.
 */
data class MotorResponse(
    /** Lista de taxas para transações no débito */
    val debito: List<TaxaBandeiraDebito>,
    /** Lista de taxas para transações no crédito */
    val credito: List<TaxaBandeiraCredito>,
    /** Lista de informações sobre vouchers (opcional) */
    val voucher: List<TaxaVoucher>? = null
)