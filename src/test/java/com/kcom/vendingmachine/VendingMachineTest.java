package com.kcom.vendingmachine;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class VendingMachineTest {


    private VendingMachine vm = new VendingMachine();

    @Test
    public void testGetChangeForNothing(){
        Collection<Coin> coins = vm.getOptimalChangeFor(0);
        assertEquals(Collections.EMPTY_LIST, coins);
    }

    @Test
    public void testGetChangeForOnePenny(){
        Collection<Coin> coins = vm.getOptimalChangeFor(1);
        Collection<Coin> expected = Arrays.asList(Coin.ONE_PENNY);
        assertEquals(expected, coins);
    }

    @Test
    public void testGetChangeForTwoPence(){
        Collection<Coin> coins = vm.getOptimalChangeFor(2);
        Collection<Coin> expected = Arrays.asList(Coin.TWO_PENCE);
        assertEquals(expected, coins);
    }
    @Test
    public void testGetChangeForThreePence(){
        Collection<Coin> coins = vm.getOptimalChangeFor(3);
        Collection<Coin> expected = Arrays.asList(Coin.TWO_PENCE, Coin.ONE_PENNY);
        assertEquals(expected, coins);
    }
    @Test
    public void testGetOptimalChangeFor(){
        Collection<Coin> solution = vm.getOptimalChangeFor(123);
        Collection<Coin> expected = Arrays.asList(Coin.ONE_POUND, Coin.TWENTY_PENCE, Coin.TWO_PENCE, Coin.ONE_PENNY);
        assertEquals(expected, solution);
    }

}
