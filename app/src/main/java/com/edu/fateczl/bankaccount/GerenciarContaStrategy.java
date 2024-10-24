package com.edu.fateczl.bankaccount;

import androidx.appcompat.app.AppCompatActivity;

import com.edu.fateczl.bankaccount.model.account.ContaBancaria;

/**
 * @author Adriano M Sanchez
 */
public interface GerenciarContaStrategy<T extends ContaBancaria> {

    T criar() throws Exception;

    void exibir(AppCompatActivity main, T conta);
}
