package com.demo.courseworkbank.view.card.adapters.card;

import com.demo.courseworkbank.model.models.Card;

public interface OnItemClickListener {
    void onClick(Card card);

    void onLongClick(Card card);
}
