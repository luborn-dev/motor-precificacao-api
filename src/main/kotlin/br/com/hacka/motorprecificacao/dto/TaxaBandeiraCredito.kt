package br.com.hacka.motorprecificacao.dto

// Representa as taxas aplicadas para transações no crédito, à vista e parcelado.

data class TaxaBandeiraCredito(
    val bandeira: String,        // VISA, MASTERCARD, etc
    val taxa_a_vista: Double,    // Ex: 3.39
    val taxa_parcelado_2a18x: Double // Ex: 5.29
)
