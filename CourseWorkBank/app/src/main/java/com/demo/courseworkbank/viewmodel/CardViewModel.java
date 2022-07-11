package com.demo.courseworkbank.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.demo.courseworkbank.App;
import com.demo.courseworkbank.model.models.Card;
import com.demo.courseworkbank.model.database.CardDao;
import com.demo.courseworkbank.model.firebase.repos.FirebaseCard;

import java.util.List;

public class CardViewModel extends ViewModel {

    private final CardDao cardDao;
    private final FirebaseCard firebaseCard;

    public CardViewModel() {
        cardDao = App.getInstance().getDatabase().cardDao();
        firebaseCard = App.getInstance().getFirebaseDatabase().firebaseCard;
    }

    public LiveData<List<Card>> getCardsLiveDataByUserId(String userUid) {
        return cardDao.getCardsByUserId(userUid);
    }

    public long addCard(Card card) {
        card.setId(cardDao.insert(card));
        firebaseCard.sendCard(card);
        return card.getId();
    }

    public void deleteCard(Card card) {
        cardDao.delete(card);
        cardDao.deleteBillByCardId(card.getId());
        firebaseCard.deleteCard(card);
    }
}
