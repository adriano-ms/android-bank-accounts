package com.edu.fateczl.bankaccount;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.edu.fateczl.bankaccount.model.account.ContaPoupanca;

/**
 * @author Adriano M Sanchez
 */
public class GerenciarContaPoupancaStrategy implements GerenciarContaStrategy<ContaPoupanca> {

    private RadioButton rbSavings;
    private EditText etOwnerName;
    private EditText etNumber;
    private EditText etIncomeDay;
    private TextView txtBalance;

    public GerenciarContaPoupancaStrategy(RadioButton rbSavings, EditText etOwnerName, EditText etNumber, EditText etIncomeDay, TextView txtBalance){
        this.rbSavings = rbSavings;
        this.etOwnerName = etOwnerName;
        this.etNumber = etNumber;
        this.etIncomeDay = etIncomeDay;
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

    public EditText getEtIncomeDay() {
        return etIncomeDay;
    }

    public void setEtIncomeDay(EditText etIncomeDay) {
        this.etIncomeDay = etIncomeDay;
    }

    public TextView getTxtBalance() {
        return txtBalance;
    }

    public void setTxtBalance(TextView txtBalance) {
        this.txtBalance = txtBalance;
    }

    @Override
    public ContaPoupanca criar() throws Exception {
        String name = etOwnerName.getText().toString();
        if(name.isEmpty() || name.isBlank())
            throw new Exception(String.valueOf(R.string.invalid_account_owner_dialog));
        int number = 0;
        try {
            number = Integer.parseInt(etNumber.getText().toString());
        } catch (NumberFormatException e) {
            throw new Exception(String.valueOf(R.string.invalid_account_number_dialog));
        }
        int day = 0;
        try {
            day = Integer.parseInt(etIncomeDay.getText().toString());
        } catch (NumberFormatException e) {
            throw new RuntimeException(String.valueOf(R.string.invalid_account_income_day_dialog));
        }
        etOwnerName.getText().clear();
        etNumber.getText().clear();
        etIncomeDay.getText().clear();
        return new ContaPoupanca(name, number, day);
    }

    @Override
    public void exibir(AppCompatActivity main, ContaPoupanca conta) {
        rbSavings.setChecked(true);
        etOwnerName.setText(conta.getCliente());
        etNumber.setText(String.valueOf(conta.getNumConta()));
        etIncomeDay.setText(String.valueOf(conta.getDiaDeRendimento()));
        txtBalance.setText(String.format("%s %.2f", main.getString(R.string.txt_balance_prefix), conta.getSaldo()));
    }
}
