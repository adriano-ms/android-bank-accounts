package com.edu.fateczl.bankaccount.model.account;

import com.edu.fateczl.bankaccount.R;

/**
 * @author Adriano M Sanchez
 */
public class ContaEspecial extends ContaBancaria {

    private float limite;

    public ContaEspecial(String cliente, int numConta, float limite) {
        super(cliente, numConta);
        this.limite = limite;
    }

    public float getLimite() {
        return limite;
    }

    public void setLimite(float limite) {
        this.limite = limite;
    }

    @Override
    public void sacar(float valor) throws Exception {
        if(valor <= 0f)
            throw new Exception(String.valueOf(R.string.withdraw_invalid_value_exception));
        if(valor > saldo + limite)
            throw new Exception(String.valueOf(R.string.withdraw_insufficient_limit_exception));
        saldo -= valor;
    }
}
