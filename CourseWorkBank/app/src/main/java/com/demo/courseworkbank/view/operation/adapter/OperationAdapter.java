package com.demo.courseworkbank.view.operation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.demo.courseworkbank.R;
import com.demo.courseworkbank.model.models.Operation;
import com.demo.courseworkbank.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OperationAdapter extends RecyclerView.Adapter<OperationAdapter.OperationViewHolder> {

    private final SortedList<Operation> operations;

    public OperationAdapter() {
        operations = new SortedList<>(Operation.class, new SortedList.Callback<Operation>() {
            @Override
            public int compare(Operation o1, Operation o2) {
                return 0;
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Operation oldItem, Operation newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Operation item1, Operation item2) {
                return item1.getId() == item2.getId();
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @NonNull
    @Override
    public OperationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OperationViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_operation, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OperationAdapter.OperationViewHolder holder, int position) {
        holder.bind(operations.get(position));
    }

    @Override
    public int getItemCount() {
        return operations.size();
    }

    public void setOperations(List<Operation> operations) {
        this.operations.replaceAll(operations);
    }

    protected static class OperationViewHolder extends RecyclerView.ViewHolder {

        Operation operation;
        private final TextView itemBillNumber;
        private final TextView itemType;
        private final TextView itemMoney;
        private final TextView itemReceiver;
        private final TextView itemDate;
        private final TextView operationReceiver;

        public OperationViewHolder(@NonNull View itemView) {
            super(itemView);
            itemBillNumber = itemView.findViewById(R.id.tv_item_bill_number);
            itemType = itemView.findViewById(R.id.tv_item_operation_type);
            itemMoney = itemView.findViewById(R.id.tv_item_operation_money);
            itemReceiver = itemView.findViewById(R.id.tv_item_operation_recivier);
            itemDate = itemView.findViewById(R.id.tv_item_operation_date);
            operationReceiver = itemView.findViewById(R.id.operation_receiver);
        }

        public void bind(Operation operation) {
            this.operation = operation;
            itemBillNumber.setText(operation.getBillNumber());
            itemType.setText(operation.getType());
            itemMoney.setText(String.valueOf(operation.getMoney()));
            if(operation.getType().equals(Constants.OPERATION_SEND)){
                itemReceiver.setText(operation.getMoneyReceiverBillNumber());
            }else{
                operationReceiver.setVisibility(View.GONE);
                itemReceiver.setVisibility(View.GONE);
            }
            String dateString = new SimpleDateFormat(Constants.TIME_STAMP_FORMAT, Locale.getDefault()).format(operation.getCreateDate());
            itemDate.setText(dateString);
        }
    }
}