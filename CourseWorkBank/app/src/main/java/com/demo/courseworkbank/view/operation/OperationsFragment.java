package com.demo.courseworkbank.view.operation;

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
import android.widget.Button;

import com.demo.courseworkbank.R;
import com.demo.courseworkbank.utils.Navigator;
import com.demo.courseworkbank.view.operation.adapter.OperationAdapter;
import com.demo.courseworkbank.view.report.ReportFragment;
import com.demo.courseworkbank.viewmodel.OperationViewModel;

public class OperationsFragment extends Fragment {

    private static final String ARG_CARD_ID = "param_card_id";

    private RecyclerView rvOperations;
    private Button btnShowReportFragment;

    private Navigator navigator;
    private OperationAdapter adapter;
    private long cardId;
    private OperationViewModel operationViewModel;

    public OperationsFragment() {
    }

    public static OperationsFragment newInstance(long cardId) {
        OperationsFragment fragment = new OperationsFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_CARD_ID, cardId);
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
            cardId = getArguments().getLong(ARG_CARD_ID);
        }
        operationViewModel = new ViewModelProvider(this).get(OperationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_operations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvOperations = view.findViewById(R.id.rv_operations);
        btnShowReportFragment = view.findViewById(R.id.btn_navigate_to_report_fragment);

        rvOperations.setLayoutManager(new LinearLayoutManager(this.getContext(),
                LinearLayoutManager.VERTICAL, false));

        adapter = new OperationAdapter();
        operationViewModel.getOperationsLiveDataByCardId(cardId).observe(this.getViewLifecycleOwner(), adapter::setOperations);
        rvOperations.setAdapter(adapter);

        btnShowReportFragment.setOnClickListener(v->navigator.navigateTo(ReportFragment.newInstance(cardId)));
    }
}