package com.kcom.vendingmachine;


import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum Coin {

    ONE_PENNY(1),
    TWO_PENCE(2),
    FIVE_PENCE(5),
    TEN_PENCE(10),
    TWENTY_PENCE(20),
    FIFTY_PENCE(50),
    ONE_POUND(100);


    private int denomination;

    Coin(int i) {
        denomination = i;
    }

    public int getDenomination() {
        return denomination;
    }

    public static Coin getMaxValueCoinForValue(int value) {
        List<Coin> coins = Arrays.stream(values()).sorted().filter(coin -> coin.getDenomination() <= value).collect(Collectors.toList());
        if (coins.isEmpty())
            throw new NoCoinFoundException("No coin found for this amount: " + value);
        else
            return coins.stream().sorted((c1, c2) -> Integer.compare(c2.getDenomination(), c1.getDenomination())).findFirst().get();
    }

    public static Coin fromValue(Integer value) {
        Optional<Coin> coinOptional = Arrays.stream(values()).filter(coin -> coin.getDenomination() == value)
                .findAny();
        if (coinOptional.isPresent())
            return coinOptional.get();
        else throw new NoCoinFoundException("No coin found for this amount: " + value);
    }

    static class NoCoinFoundException extends RuntimeException {
        public NoCoinFoundException(String s) {
            super(s);
        }
    }
}
