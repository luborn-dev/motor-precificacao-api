package br.com.hacka.motorprecificacao.dto

// Classe de resposta que contém as listas de taxas de débito, crédito e voucher.

data class MotorResponse (
    val debito: List<TaxaBandeiraDebito>,
    val credito: List<TaxaBandeiraCredito>,
    val voucher: List<TaxaVoucher>? = null  // opcional
)