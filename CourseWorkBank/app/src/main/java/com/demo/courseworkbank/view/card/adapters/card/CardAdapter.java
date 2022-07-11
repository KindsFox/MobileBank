package com.demo.courseworkbank.view.card.adapters.card;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.demo.courseworkbank.R;
import com.demo.courseworkbank.model.models.Card;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private final SortedList<Card> cards;
    private final OnItemClickListener listener;

    public CardAdapter(OnItemClickListener listener) {
        cards = new SortedList<>(Card.class, new SortedList.Callback<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return 0;
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Card oldItem, Card newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Card item1, Card item2) {
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
    public CardAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardAdapter.CardViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.CardViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> listener.onClick(cards.get(position)));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onLongClick(cards.get(position));
            cards.removeItemAt(position);
            return true;
        });
        holder.bind(cards.get(position));
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public void setCards(List<Card> cards) {
        this.cards.replaceAll(cards);
    }

    protected static class CardViewHolder extends RecyclerView.ViewHolder {

        Card card;
        private final TextView itemCardNumber;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            itemCardNumber = itemView.findViewById(R.id.tv_code_number);
        }

        public void bind(Card card) {
            this.card = card;
            itemCardNumber.setText(card.getCodeNumber());
        }
    }
}