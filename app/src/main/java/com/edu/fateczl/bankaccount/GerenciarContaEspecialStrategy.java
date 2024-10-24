package com.edu.fateczl.bankaccount;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.edu.fateczl.bankaccount.model.account.ContaEspecial;

/**
 * @author Adriano M Sanchez
 */
public class GerenciarContaEspecialStrategy implements GerenciarContaStrategy<ContaEspecial> {

    private RadioButton rbSpecial;
    private EditText etOwnerName;
    private EditText etNumber;
    private EditText etLimit;
    private TextView txtBalance;

    public GerenciarContaEspecialStrategy(RadioButton rbSpecial, EditText etOwnerName, EditText etNumber, EditText etIncomeDay, TextView txtBalance){
        this.rbSpecial = rbSpecial;
        this.etOwnerName = etOwnerName;
        this.etNumber = etNumber;
        this.etLimit = etIncomeDay;
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

    public EditText getEtLimit() {
        return etLimit;
    }

    public void setEtLimit(EditText etLimit) {
        this.etLimit = etLimit;
    }

    public TextView getTxtBalance() {
        return txtBalance;
    }

    public void setTxtBalance(TextView txtBalance) {
        this.txtBalance = txtBalance;
    }

    @Override
    public ContaEspecial criar() throws Exception {
        String name = etOwnerName.getText().toString();
        if(name.isEmpty() || name.isBlank())
            throw new Exception(String.valueOf(R.string.invalid_account_owner_dialog));
        int number = 0;
        try {
            number = Integer.parseInt(etNumber.getText().toString());
        } catch (NumberFormatException e) {
            throw new Exception(String.valueOf(R.string.invalid_account_number_dialog));
        }
        float limit = 0;
        try {
            limit = Float.parseFloat(etLimit.getText().toString());
        } catch (NumberFormatException e) {
            throw new RuntimeException(String.valueOf(R.string.invalid_account_limit_dialog));
        }
        etOwnerName.getText().clear();
        etNumber.getText().clear();
        etLimit.getText().clear();
        return new ContaEspecial(name, number, limit);
    }

    @Override
    public void exibir(AppCompatActivity main, ContaEspecial conta) {
        rbSpecial.setChecked(true);
        etOwnerName.setText(conta.getCliente());
        etNumber.setText(String.valueOf(conta.getNumConta()));
        etLimit.setText(String.valueOf(conta.getLimite()));
        txtBalance.setText(String.format("%s %.2f", main.getString(R.string.txt_balance_prefix), conta.getSaldo()));
    }
}
