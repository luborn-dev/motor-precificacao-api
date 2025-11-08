package br.com.hacka.motorprecificacao.dto

// Representa as taxas aplicadas para transações no débito.

data class TaxaBandeiraDebito(
    val bandeira: String,        // VISA, MASTERCARD, ELO, HIPER, etc
    val taxa_a_vista: Double     // Ex: 2.39
)
