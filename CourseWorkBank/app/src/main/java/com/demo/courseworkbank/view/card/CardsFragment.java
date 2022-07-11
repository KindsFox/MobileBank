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

import com.demo.courseworkbank.App;
import com.demo.courseworkbank.R;
import com.demo.courseworkbank.model.models.Card;
import com.demo.courseworkbank.model.models.User;
import com.demo.courseworkbank.utils.CardDataRandomizer;
import com.demo.courseworkbank.utils.Navigator;
import com.demo.courseworkbank.view.card.adapters.card.CardAdapter;
import com.demo.courseworkbank.view.card.adapters.card.OnItemClickListener;
import com.demo.courseworkbank.viewmodel.CardViewModel;
import com.demo.courseworkbank.viewmodel.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class CardsFragment extends Fragment {

    private RecyclerView rvCards;
    private FloatingActionButton fabAddCard;

    private Navigator navigator;
    private CardViewModel cardViewModel;
    private UserViewModel userViewModel;
    private CardAdapter adapter;

    private User user;

    public CardsFragment() {
    }

    public static CardsFragment newInstance() {
        return new CardsFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        navigator = (Navigator) context;
        cardViewModel = new ViewModelProvider(this).get(CardViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        user = userViewModel.getUser(App.getInstance().getFirebaseAuth().getUser().getEmail());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cards, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvCards = view.findViewById(R.id.rv_cards);
        fabAddCard = view.findViewById(R.id.fab_add_card);

        rvCards.setLayoutManager(new LinearLayoutManager(this.getContext(),
                LinearLayoutManager.VERTICAL, false));

        adapter = new CardAdapter(new OnItemClickListener() {
            @Override
            public void onClick(Card card) {
                navigator.navigateTo(CardInfoFragment.newInstance(card, user));
            }

            @Override
            public void onLongClick(Card card) {
                cardViewModel.deleteCard(card);
            }
        });

        cardViewModel.getCardsLiveDataByUserId(user.getUid())
                .observe(this.getViewLifecycleOwner(), adapter::setCards);
        rvCards.setAdapter(adapter);

        fabAddCard.setOnClickListener(v ->
                cardViewModel.addCard(
                        new Card(CardDataRandomizer.getCardCvv(),
                                CardDataRandomizer.getCardNumber(),
                                CardDataRandomizer.getCardDate(),
                                user.getUid())));
    }
}