package com.kcom.vendingmachine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class VendingMachine {


    public Collection<Coin> getOptimalChangeFor(int pence) {
        if (pence <= 0) return Collections.EMPTY_LIST;
        else return getOptimalChangeForPositiveAmounts(pence);
    }

    protected Collection<Coin> getOptimalChangeForPositiveAmounts(int pence) {
        Collection<Coin> result = new ArrayList<>();
        int amount = pence;
        while (amount > 0) {
            Coin coin = Coin.getMaxValueCoinForValue(amount);
            amount = amount - coin.getDenomination();
            result.add(coin);
        }
        return result;
    }

}
