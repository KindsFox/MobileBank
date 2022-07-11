package com.demo.courseworkbank.view.operation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.demo.courseworkbank.R;
import com.demo.courseworkbank.model.models.Bill;
import com.demo.courseworkbank.model.models.Operation;
import com.demo.courseworkbank.utils.Constants;
import com.demo.courseworkbank.viewmodel.BillViewModel;
import com.demo.courseworkbank.viewmodel.OperationViewModel;

import java.util.Date;


public class OperationDialog extends Dialog implements View.OnClickListener {

    private final Activity activity;
    private EditText etMoney;
    private EditText etBillNumber;
    private TextView tvCancel;
    private TextView tvDo;
    private Spinner spinnerOperations;

    private final OperationViewModel operationViewModel;
    private final BillViewModel billViewModel;
    private final long cardId;
    private final Bill bill;

    String[] operations = {Constants.OPERATION_SEND, Constants.OPERATION_TAKE_OFF_MONEY};

    public OperationDialog(Activity activity, long cardId, Bill bill) {
        super(activity);
        this.activity = activity;
        this.cardId = cardId;
        this.bill = bill;
        operationViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(OperationViewModel.class);
        billViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(BillViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_operation);
        etMoney = findViewById(R.id.et_dialog_money_count);
        etBillNumber = findViewById(R.id.et_dialog_bill_number);
        tvCancel = findViewById(R.id.tv_cancel);
        tvDo = findViewById(R.id.tv_do);
        spinnerOperations = findViewById(R.id.spinner_operations);

        tvCancel.setOnClickListener(this);
        tvDo.setOnClickListener(this);
        fillSpinner();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_do:
                String operationType = spinnerOperations.getSelectedItem().toString();
                double money = Double.parseDouble(etMoney.getText().toString());
                if (bill.canDecreaseMoney(money)) {
                    Bill bill;
                    if (operationType.equals(Constants.OPERATION_SEND)) {
                        bill = billViewModel.getBillByBillNumber(etBillNumber.getText().toString());
                        if (bill != null) {
                            bill.IncreaseMoney(money);
                            billViewModel.editBill(bill);
                        } else {
                            Toast.makeText(activity, "Такого счета не существует", Toast.LENGTH_LONG).show();
                            dismiss();
                            break;
                        }
                    }
                    operationViewModel.addOperation(
                            new Operation(
                                    operationType,
                                    this.bill.getBillNumber(),
                                    new Date(),
                                    money,
                                    etBillNumber.getText().toString(),
                                    cardId
                            ));
                    this.bill.decreaseMoney(money);
                    billViewModel.editBill(this.bill);
                    dismiss();
                }
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    private void fillSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, operations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOperations.setAdapter(adapter);
        spinnerOperations.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerOperations.getSelectedItem().toString().equals(Constants.OPERATION_SEND)) {
                    etBillNumber.setVisibility(View.VISIBLE);
                } else {
                    etBillNumber.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerOperations.setSelection(0);
    }
}