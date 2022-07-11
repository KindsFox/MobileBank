package com.demo.courseworkbank.view.card;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.demo.courseworkbank.R;
import com.demo.courseworkbank.model.models.Bill;
import com.demo.courseworkbank.model.models.Card;
import com.demo.courseworkbank.model.models.User;
import com.demo.courseworkbank.utils.BillDataRandomizer;
import com.demo.courseworkbank.utils.CardDataRandomizer;
import com.demo.courseworkbank.utils.Constants;
import com.demo.courseworkbank.utils.Navigator;
import com.demo.courseworkbank.view.card.adapters.bill.BillAdapter;
import com.demo.courseworkbank.view.card.adapters.bill.OnItemClickListener;
import com.demo.courseworkbank.view.operation.OperationDialog;
import com.demo.courseworkbank.view.operation.OperationsFragment;
import com.demo.courseworkbank.viewmodel.BillViewModel;
import com.demo.courseworkbank.viewmodel.CardViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class CardInfoFragment extends Fragment {

    private static final String ARG_CARD = "param_card";
    private static final String ARG_USER = "param_user";

    private TextView tvCardCode;
    private TextView tvCardFIO;
    private TextView tvCardCvv;
    private TextView tvCardExpirationDate;
    private FloatingActionButton fabGoBack;
    private ImageButton ibAddBill;
    private ImageButton ibShowOperations;
    private RecyclerView rvBills;

    private Navigator navigator;
    private BillAdapter adapter;
    private CardViewModel cardViewModel;
    private BillViewModel billViewModel;
    private Card card;
    private User user;

    public CardInfoFragment() {
    }

    public static CardInfoFragment newInstance(Card card, User user) {
        CardInfoFragment fragment = new CardInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CARD, card);
        args.putParcelable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        navigator = (Navigator) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            card = getArguments().getParcelable(ARG_CARD);
            user = getArguments().getParcelable(ARG_USER);
        }
        cardViewModel = new ViewModelProvider(this).get(CardViewModel.class);
        billViewModel = new ViewModelProvider(this).get(BillViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvCardCode = view.findViewById(R.id.tv_card_info_code);
        tvCardFIO = view.findViewById(R.id.tv_card_info_fio);
        tvCardCvv = view.findViewById(R.id.tv_card_info_cvv);
        tvCardExpirationDate = view.findViewById(R.id.tv_card_info_expiration_date);
        fabGoBack = view.findViewById(R.id.fab_card_info_go_back);
        ibAddBill = view.findViewById(R.id.ib_add_bill_to_card);
        rvBills = view.findViewById(R.id.rv_bills);
        ibShowOperations = view.findViewById(R.id.ib_show_operations);

        rvBills.setLayoutManager(new LinearLayoutManager(this.getContext(),
                LinearLayoutManager.VERTICAL, false));

        adapter = new BillAdapter(new OnItemClickListener() {
            @Override
            public void onClick(Bill bill) {
                showOperationAlert(bill);
            }

            @Override
            public void onLongClick(Bill bill) {
                billViewModel.deleteBill(bill);
            }
        });

        billViewModel.getBillsLiveDataByCardId(card.getId())
                .observe(this.getViewLifecycleOwner(), adapter::setBills);
        rvBills.setAdapter(adapter);

        setCardData();
        setUpListeners();
    }

    private void setCardData() {
        String dateString = new SimpleDateFormat(Constants.DOB_FORMAT, Locale.getDefault()).format(CardDataRandomizer.getCardDate());
        tvCardCode.setText(card.getCodeNumber());
        tvCardFIO.setText(user.getFIO());
        tvCardCvv.setText(String.valueOf(card.getCvv()));
        tvCardExpirationDate.setText(dateString);
    }

    private void setUpListeners() {
        ibAddBill.setOnClickListener(v ->
                billViewModel.addBill(new Bill(
                        BillDataRandomizer.getRandomBillNumber(),
                        BillDataRandomizer.getRandomMoneys(),
                        card.getId()))
        );

        fabGoBack.setOnClickListener(v ->
                requireActivity().onBackPressed()
        );
        ibShowOperations.setOnClickListener(v->{
            navigator.navigateTo(OperationsFragment.newInstance(card.getId()));
        });
    }

    private void showOperationAlert(Bill bill) {
        OperationDialog dialog = new OperationDialog(
                requireActivity(),
                card.getId(),
                bill
        );
        dialog.show();
    }
}