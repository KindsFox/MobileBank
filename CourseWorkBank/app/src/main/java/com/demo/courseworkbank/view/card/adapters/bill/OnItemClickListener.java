package com.demo.courseworkbank.view.card.adapters.bill;

import com.demo.courseworkbank.model.models.Bill;

public interface OnItemClickListener {
    void onClick(Bill bill);

    void onLongClick(Bill bill);
}
