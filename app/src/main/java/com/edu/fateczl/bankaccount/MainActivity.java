package com.edu.fateczl.bankaccount;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.edu.fateczl.bankaccount.model.account.ContaBancaria;
import com.edu.fateczl.bankaccount.model.account.ContaEspecial;
import com.edu.fateczl.bankaccount.model.account.ContaPoupanca;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Adriano M Sanchez
 */
public class MainActivity extends AppCompatActivity {

    private RadioGroup rgAccountTypes;
    private RadioButton rbNormal;
    private RadioButton rbSavings;
    private RadioButton rbSpecial;
    private CheckBox cbQuery;
    private EditText etOwnerName;
    private EditText etNumber;
    private EditText etIncomeDay;
    private EditText etLimit;

    private Button btCreate;

    private TextView txtBalance;
    private TextView txtErrors;

    private RadioGroup rgOperation;
    private RadioButton rbWithdraw;
    private RadioButton rbDeposit;
    private EditText etValue;
    private Button btOperation;

    private EditText etIncome;
    private Button btIncome;

    private Set<View> lockedWhenQueryChecked;
    private Set<View> hiddenWhenCurrentAccountIsNull;

    private Set<ContaBancaria> accounts;

    private ContaBancaria currentAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        hiddenWhenCurrentAccountIsNull = new HashSet<>();
        lockedWhenQueryChecked = new HashSet<>();

        accounts = new HashSet<>();

        initializeViews();
        fillSets();

        setCurrentAccount(null);
        queryChecked(false);
        cbQuery.setOnClickListener(cb -> queryChecked(cbQuery.isChecked()));

        accountTypeSwitch(rgAccountTypes.getCheckedRadioButtonId());
        rgAccountTypes.setOnCheckedChangeListener((rg, rb) -> accountTypeSwitch(rb));

        btCreate.setOnClickListener(bt -> {
            if(cbQuery.isChecked()){
                query();
            } else {
                create();
            }
        });

        btOperation.setOnClickListener(bt -> {
            txtErrors.setText("");
            if(currentAccount != null){
                try {
                    float value= Float.parseFloat(etValue.getText().toString());
                    etValue.getText().clear();
                    if(rgOperation.getCheckedRadioButtonId() == rbWithdraw.getId())
                        currentAccount.sacar(value);
                    else
                        currentAccount.depositar(value);
                    etNumber.setText(String.valueOf(currentAccount.getNumConta()));
                    query();
                }catch (NumberFormatException ex) {
                    txtErrors.setText(rgOperation.getCheckedRadioButtonId() == rbWithdraw.getId() ?
                            getString(R.string.withdraw_invalid_value_exception) : getString(R.string.deposit_invalid_value_exception));
                } catch (Exception e) {
                    txtErrors.setText(getString(Integer.parseInt(e.getMessage())));
                }
            }
        });

        btIncome.setOnClickListener(bt -> {
            txtErrors.setText("");
            if(currentAccount != null){
                try {
                    float value= Float.parseFloat(etIncome.getText().toString());
                    etIncome.getText().clear();
                    ((ContaPoupanca) currentAccount).calcularNovoSaldo(value);
                    etNumber.setText(String.valueOf(currentAccount.getNumConta()));
                    query();
                }catch (NumberFormatException ex) {
                    txtErrors.setText(getString(R.string.withdraw_invalid_value_exception));
                } catch (Exception e) {
                    txtErrors.setText(getString(Integer.parseInt(e.getMessage())));
                }
            }
        });
    }


    private void query() {
        try {
            int accountNumber = Integer.parseInt(etNumber.getText().toString());
            ContaBancaria cb = accounts.stream().filter(c -> c.getNumConta() == accountNumber).findFirst()
                    .orElseThrow(() -> {
                        etNumber.setText(currentAccount == null ? etNumber.getText().toString() : String.valueOf(currentAccount.getNumConta()));
                        return new RuntimeException(getString(R.string.account_not_found_error));
                    });
            chooseStrategy(cb.getClass()).exibir(this, cb);
            setCurrentAccount(cb);
        } catch (NumberFormatException e) {
            new AlertDialog.Builder(this).setTitle(R.string.txt_error).setMessage(getString(R.string.invalid_account_number_dialog)).show();
        } catch (Exception e){
            new AlertDialog.Builder(this).setTitle(R.string.txt_error).setMessage(e.getMessage()).show();
        }
    }

    private void create(){
        try {
            accounts.add(chooseStrategy(accountTypeSwitch(rgAccountTypes.getCheckedRadioButtonId())).criar());
        } catch (Exception e) {
            new AlertDialog.Builder(this).setTitle(R.string.txt_error).setMessage(getString(Integer.parseInt(e.getMessage()))).show();
        }
    }

    private void setCurrentAccount(ContaBancaria c){
        currentAccount = c;
        if(c == null)
            hiddenWhenCurrentAccountIsNull.forEach(v -> v.setVisibility(View.INVISIBLE));
        else {
            hiddenWhenCurrentAccountIsNull.forEach(v -> v.setVisibility(View.VISIBLE));
            if(!c.getClass().equals(ContaPoupanca.class)) {
                etIncome.setVisibility(View.INVISIBLE);
                btIncome.setVisibility(View.INVISIBLE);
            } else {
                etIncome.setVisibility(View.VISIBLE);
                btIncome.setVisibility(View.VISIBLE);
            }
        }

    }

    private void fillSets() {
        lockedWhenQueryChecked.add(rbNormal);
        lockedWhenQueryChecked.add(rbSavings);
        lockedWhenQueryChecked.add(rbSpecial);
        lockedWhenQueryChecked.add(etOwnerName);
        lockedWhenQueryChecked.add(etIncomeDay);
        lockedWhenQueryChecked.add(etLimit);

        hiddenWhenCurrentAccountIsNull.add(txtBalance);
        hiddenWhenCurrentAccountIsNull.add(txtErrors);
        hiddenWhenCurrentAccountIsNull.add(rgOperation);
        hiddenWhenCurrentAccountIsNull.add(etValue);
        hiddenWhenCurrentAccountIsNull.add(btOperation);
        hiddenWhenCurrentAccountIsNull.add(etIncome);
        hiddenWhenCurrentAccountIsNull.add(btIncome);
    }

    private void initializeViews() {
        rgAccountTypes = findViewById(R.id.rgAccountTypes);
        rbNormal = findViewById(R.id.rbNormal);
        rbSavings = findViewById(R.id.rbSalvings);
        rbSpecial = findViewById(R.id.rbSpecial);
        cbQuery = findViewById(R.id.cbQuery);
        etOwnerName = findViewById(R.id.etOwnerName);
        etNumber = findViewById(R.id.etNumber);
        etIncomeDay = findViewById(R.id.etIncomeDay);
        etLimit = findViewById(R.id.etLimit);

        btCreate = findViewById(R.id.btCreate);

        txtBalance = findViewById(R.id.txtBalance);
        txtErrors = findViewById(R.id.txtErrors);

        rgOperation = findViewById(R.id.rgOperation);
        rbWithdraw = findViewById(R.id.rbWithdraw);
        rbDeposit = findViewById(R.id.rbDeposit);
        etValue = findViewById(R.id.etValue);
        btOperation = findViewById(R.id.btOperation);

        etIncome = findViewById(R.id.etIncome);
        btIncome = findViewById(R.id.btIncome);
    }

    private void queryChecked(boolean checked){
        lockedWhenQueryChecked.forEach(v -> v.setEnabled(!checked));
        btCreate.setText(checked ? R.string.bt_query : R.string.bt_create);
    }

    private <T extends ContaBancaria> Class<T> accountTypeSwitch(int rb){
        if(rb == rbSavings.getId()) {
            etLimit.setVisibility(View.INVISIBLE);
            etIncomeDay.setVisibility(View.VISIBLE);
            return (Class<T>) ContaPoupanca.class;
        }
        if(rb == rbSpecial.getId()) {
            etLimit.setVisibility(View.VISIBLE);
            etIncomeDay.setVisibility(View.INVISIBLE);
            return (Class<T>) ContaEspecial.class;
        }
        etLimit.setVisibility(View.INVISIBLE);
        etIncomeDay.setVisibility(View.INVISIBLE);
        return (Class<T>) ContaBancaria.class;
    }

    private <T extends ContaBancaria> GerenciarContaStrategy<T> chooseStrategy(Class<? extends ContaBancaria> clazz){
        if(clazz.equals(ContaPoupanca.class))
            return (GerenciarContaStrategy<T>) new GerenciarContaPoupancaStrategy(rbSavings, etOwnerName, etNumber, etIncomeDay, txtBalance);
        if(clazz.equals(ContaEspecial.class))
            return (GerenciarContaStrategy<T>) new GerenciarContaEspecialStrategy(rbSpecial, etOwnerName, etNumber, etLimit, txtBalance);
        return (GerenciarContaStrategy<T>) new GerenciarContaNormalStrategy(rbNormal, etOwnerName, etNumber, txtBalance);
    }
}