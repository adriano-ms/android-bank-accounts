package com.edu.fateczl.bankaccount.model.account;

import com.edu.fateczl.bankaccount.R;

import java.util.Objects;

/**
 * @author Adriano M Sanchez
 */
public class ContaBancaria {

    protected String cliente;
    protected int numConta;
    protected float saldo;

    public ContaBancaria(String cliente, int numConta){
        this.cliente = cliente;
        this.numConta = numConta;
        this.saldo = 0f;
    }

    public String getCliente() {
        return cliente;
    }

    public int getNumConta() {
        return numConta;
    }

    public float getSaldo() {
        return saldo;
    }

    public void sacar(float valor) throws Exception {
        if(valor <= 0f)
            throw new Exception(String.valueOf(R.string.withdraw_invalid_value_exception));
        if(valor > saldo)
            throw new Exception(String.valueOf(R.string.withdraw_insufficient_balance_exception));
        saldo -= valor;
    }

    public void depositar(float valor) throws Exception {
        if(valor <= 0f)
            throw new Exception(String.valueOf(R.string.deposit_invalid_value_exception));
        saldo += valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContaBancaria that = (ContaBancaria) o;
        return numConta == that.numConta;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numConta);
    }
}
