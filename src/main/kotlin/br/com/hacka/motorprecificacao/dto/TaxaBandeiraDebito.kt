package br.com.hacka.motorprecificacao.dto

/**
 * DTO que representa as taxas aplicadas para transações no débito.
 *
 * Inclui a bandeira do cartão e a taxa aplicada.
 */
data class TaxaBandeiraDebito(
    /** Bandeira do cartão (VISA, MASTERCARD, ELO, HIPER, etc) */
    val bandeira: String,
    /** Taxa a vista em porcentagem (ex: 2.39) */
    val taxa_a_vista: Double
)
