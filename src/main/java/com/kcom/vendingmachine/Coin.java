package com.kcom.vendingmachine;


import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Optional;

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
        Optional<Coin> first = Arrays.stream(values()).sorted((c1, c2) -> Integer.compare(c2.getDenomination(), c1.getDenomination())).filter(coin -> coin.getDenomination() <= value).findFirst();
        if (first.isPresent()) {
            return first.get();
        } else {
            throw new NoCoinFoundException("No coin found for this amount: " + value);
        }
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

        public NoCoinFoundException(String s, Exception e) {
            super(s, e);
        }
    }
}
