package com.kcom.vendingmachine;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoinTest {

    @Test(expected = Coin.NoCoinFoundException.class)
    public void getOnePennyCoinForValue0(){
        Coin.getMaxValueCoinForValue(0);
    }

    @Test
    public void getOnePennyCoinForValue1(){
        assertEquals(Coin.ONE_PENNY, Coin.getMaxValueCoinForValue(1));
    }

    @Test
    public void getTwoPenceCoinForValue2(){
        assertEquals(Coin.TWO_PENCE, Coin.getMaxValueCoinForValue(2));
    }

    @Test
    public void getTwoPenceCoinForValue3(){
        assertEquals(Coin.TWO_PENCE, Coin.getMaxValueCoinForValue(3));
    }

    @Test
    public void getFivePenceCoinForValue5(){
        assertEquals(Coin.FIVE_PENCE, Coin.getMaxValueCoinForValue(5));
    }

    @Test
    public void getFivePenceCoinForValue7(){
        assertEquals(Coin.FIVE_PENCE, Coin.getMaxValueCoinForValue(7));
    }

    @Test
    public void testFromValue(){
        assertEquals(Coin.FIVE_PENCE, Coin.fromValue(5));
    }
    @Test(expected = Coin.NoCoinFoundException.class)
    public void testFromValueFail(){
        Coin.fromValue(7);
    }
}
