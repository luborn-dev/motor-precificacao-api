package br.com.hacka.motorprecificacao.dto

// Representa as taxas ou informações associadas a vouchers como Alelo ou Sodexo.

data class TaxaVoucher(
    val nome: String,            // Alelo, Sodexo, Ticket
    val observacao: String? = null
)
