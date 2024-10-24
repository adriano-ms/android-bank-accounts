package com.edu.fateczl.bankaccount.model.account;

import com.edu.fateczl.bankaccount.R;

/**
 * @author Adriano M Sanchez
 */
public class ContaPoupanca extends ContaBancaria {

    private int diaDeRendimento;

    public ContaPoupanca(String cliente, int numConta, int diaDeRendimento) {
        super(cliente, numConta);
        this.diaDeRendimento = diaDeRendimento;
    }

    public int getDiaDeRendimento() {
        return diaDeRendimento;
    }

    public void setDiaDeRendimento(int diaDeRendimento) {
        this.diaDeRendimento = diaDeRendimento;
    }

    public void calcularNovoSaldo(float taxaDeRendimento) throws Exception {
        if(taxaDeRendimento <= 0f)
            throw new Exception(String.valueOf(R.string.income_invalid_value_exception));
        float rendimento = getSaldo() * (taxaDeRendimento / 100f);
        saldo += rendimento;
    }
}
