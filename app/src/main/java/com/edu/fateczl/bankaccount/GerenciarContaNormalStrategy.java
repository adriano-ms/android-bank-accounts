package com.edu.fateczl.bankaccount;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.edu.fateczl.bankaccount.model.account.ContaBancaria;

/**
 * @author Adriano M Sanchez
 */
public class GerenciarContaNormalStrategy implements GerenciarContaStrategy<ContaBancaria> {

    private RadioButton rbNormal;
    private EditText etOwnerName;
    private EditText etNumber;
    private TextView txtBalance;


    public GerenciarContaNormalStrategy(RadioButton rbNormal, EditText etOwnerName, EditText etNumber, TextView txtBalance){
        this.rbNormal = rbNormal;
        this.etOwnerName = etOwnerName;
        this.etNumber = etNumber;
        this.txtBalance = txtBalance;
    }

    public EditText getEtOwnerName() {
        return etOwnerName;
    }

    public void setEtOwnerName(EditText etOwnerName) {
        this.etOwnerName = etOwnerName;
    }

    public EditText getEtNumber() {
        return etNumber;
    }

    public void setEtNumber(EditText etNumber) {
        this.etNumber = etNumber;
    }

    public TextView getTxtBalance() {
        return txtBalance;
    }

    public void setTxtBalance(TextView txtBalance) {
        this.txtBalance = txtBalance;
    }

    @Override
    public ContaBancaria criar() throws Exception {
        String name = etOwnerName.getText().toString();
        if(name.isEmpty() || name.isBlank())
            throw new Exception(String.valueOf(R.string.invalid_account_owner_dialog));
        int number = 0;
        try {
            number = Integer.parseInt(etNumber.getText().toString());
        } catch (NumberFormatException e) {
            throw new Exception(String.valueOf(R.string.invalid_account_number_dialog));
        }
        etOwnerName.getText().clear();
        etNumber.getText().clear();
        return new ContaBancaria(name, number);
    }

    @Override
    public void exibir(AppCompatActivity main, ContaBancaria conta) {
        rbNormal.setChecked(true);
        etOwnerName.setText(conta.getCliente());
        etNumber.setText(String.valueOf(conta.getNumConta()));
        txtBalance.setText(String.format("%s %.2f", main.getString(R.string.txt_balance_prefix), conta.getSaldo()));
    }
}
