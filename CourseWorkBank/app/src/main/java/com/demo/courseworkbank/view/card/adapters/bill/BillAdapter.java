package com.demo.courseworkbank.view.card.adapters.bill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.demo.courseworkbank.R;
import com.demo.courseworkbank.model.models.Bill;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {

    private final SortedList<Bill> bills;
    private final OnItemClickListener listener;

    public BillAdapter(OnItemClickListener listener) {
        bills = new SortedList<>(Bill.class, new SortedList.Callback<Bill>() {
            @Override
            public int compare(Bill o1, Bill o2) {
                return 0;
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Bill oldItem, Bill newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Bill item1, Bill item2) {
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
        this.listener = listener;
    }

    @NonNull
    @Override
    public BillAdapter.BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BillAdapter.BillViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bill, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BillAdapter.BillViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> listener.onClick(bills.get(position)));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onLongClick(bills.get(position));
            bills.removeItemAt(position);
            return true;
        });
        holder.bind(bills.get(position));
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    public void setBills(List<Bill> bills) {
        this.bills.replaceAll(bills);
    }

    protected static class BillViewHolder extends RecyclerView.ViewHolder {

        Bill bill;
        private final TextView itemBillNumber;
        private final TextView itemBillMoney;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            itemBillNumber = itemView.findViewById(R.id.tv_bill_number);
            itemBillMoney = itemView.findViewById(R.id.tv_bill_money);
        }

        public void bind(Bill bill) {
            this.bill = bill;
            itemBillNumber.setText(String.valueOf(bill.getBillNumber()));
            itemBillMoney.setText(String.valueOf(bill.getMoney()));
        }
    }
}