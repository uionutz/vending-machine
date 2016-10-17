package com.kcom.vendingmachine;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@Slf4j
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


    @Test
    public void testGetChangeFor11Pence(){
        vm.loadPropsFile("coin-inventory11.properties");
        Collection<Coin> coins = vm.getOptimalChangeFor(11);
        Collection<Coin> expected = Arrays.asList(Coin.FIVE_PENCE, Coin.TWO_PENCE, Coin.TWO_PENCE, Coin.TWO_PENCE);
        assertEquals(expected, coins);
    }


    @Test(expected = Coin.NoCoinFoundException.class)
    public void testGetChangeFor90Pence(){
        vm.loadPropsFile("coin-inventory90.properties");
        vm.getOptimalChangeFor(90);
    }


    @Test
    public void testGetChangeFor21Pence(){
        vm.loadPropsFile("coin-inventory21.properties");
        Collection<Coin> coins = vm.getOptimalChangeFor(21);
        Collection<Coin> expected = Arrays.asList(Coin.TEN_PENCE, Coin.FIVE_PENCE, Coin.TWO_PENCE, Coin.TWO_PENCE, Coin.TWO_PENCE);
        assertEquals(expected, coins);
    }
}
