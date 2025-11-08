package br.com.hacka.motorprecificacao.dto

/**
 * DTO que representa as taxas aplicadas para transações no crédito.
 *
 * Inclui a bandeira do cartão, taxa à vista e taxa para parcelamento de 2 a 18x.
 */
data class TaxaBandeiraCredito(
    /** Bandeira do cartão (VISA, MASTERCARD, ELO, etc) */
    val bandeira: String,
    /** Taxa à vista em porcentagem (ex: 3.39) */
    val taxa_a_vista: Double,
    /** Taxa para parcelamento de 2 a 18x em porcentagem (ex: 5.29) */
    val taxa_parcelado_2a18x: Double
)
