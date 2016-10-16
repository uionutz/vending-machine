package com.kcom.vendingmachine;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class VendingMachineLtdTest {

    VendingMachineLtd vm = new VendingMachineLtd();

    @Test
    public void propsFileLoadedIsNotEmpty(){
        vm.loadPropsFile("coin-inventory.properties");
        assertFalse(vm.getCoinsInventory().isEmpty());
    }

    @Test
    public void testGetChangeForNothing(){
        vm.loadPropsFile("coin-inventory.properties");
        Collection<Coin> coins = vm.getOptimalChangeFor(0);
        assertEquals(Collections.EMPTY_LIST, coins);
    }

    @Test
    public void testGetChangeForOnePenny(){
        vm.loadPropsFile("coin-inventory.properties");
        Collection<Coin> coins = vm.getOptimalChangeFor(1);
        Collection<Coin> expected = Arrays.asList(Coin.ONE_PENNY);
        assertEquals(expected, coins);
    }

    @Test
    public void testGetChangeForTwoPence(){
        vm.loadPropsFile("coin-inventory1.properties");
        Collection<Coin> coins = vm.getOptimalChangeFor(2);
        Collection<Coin> expected = Arrays.asList(Coin.ONE_PENNY, Coin.ONE_PENNY);
        assertEquals(expected, coins);
    }



}
