package com.kcom.vendingmachine;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@Slf4j
public class VendingMachineLtdTest {

    private static final String TEST_RESOURCES_PATH = "src/test/resources/";
    VendingMachineLtd vm = new VendingMachineLtd();

    @Test
    public void propsFileLoadedIsNotEmpty() throws IOException {
        vm.loadPropsFile(TEST_RESOURCES_PATH + "coin-inventory.properties");
        assertFalse(vm.getCoinsInventory().isEmpty());
    }

    @Test(expected = FileNotFoundException.class)
    public void propsFileNotLoaded() throws IOException {
        vm.loadPropsFile("wrong/path/coin-inventory.properties");
    }

    @Test
    public void testGetChangeForNothing() throws IOException {
        vm.loadPropsFile(TEST_RESOURCES_PATH + "coin-inventory.properties");
        Collection<Coin> coins = vm.getChangeFor(0);
        assertEquals(Collections.emptyList(), coins);
    }

    @Test
    public void testGetChangeForOnePenny() throws IOException {
        vm.loadPropsFile(TEST_RESOURCES_PATH + "coin-inventory.properties");
        Collection<Coin> coins = vm.getChangeFor(1);
        Collection<Coin> expected = Arrays.asList(Coin.ONE_PENNY);
        assertEquals(expected, coins);
    }

    @Test
    public void testGetChangeForTwoPence() throws IOException {
        vm.loadPropsFile(TEST_RESOURCES_PATH + "coin-inventory1.properties");
        Collection<Coin> coins = vm.getChangeFor(2);
        Collection<Coin> expected = Arrays.asList(Coin.ONE_PENNY, Coin.ONE_PENNY);
        assertEquals(expected, coins);
    }


    @Test
    public void testGetChangeFor11Pence() throws IOException {
        vm.loadPropsFile(TEST_RESOURCES_PATH + "coin-inventory11.properties");
        Collection<Coin> coins = vm.getChangeFor(11);
        Collection<Coin> expected = Arrays.asList(Coin.FIVE_PENCE, Coin.TWO_PENCE, Coin.TWO_PENCE, Coin.TWO_PENCE);
        assertEquals(expected, coins);
    }


    @Test(expected = Coin.NoCoinFoundException.class)
    public void testGetChangeFor90Pence() throws IOException {
        vm.loadPropsFile(TEST_RESOURCES_PATH + "coin-inventory90.properties");
        vm.getChangeFor(90);
    }


    @Test
    public void testGetChangeFor21Pence() throws IOException {
        vm.loadPropsFile(TEST_RESOURCES_PATH + "coin-inventory21.properties");
        Collection<Coin> coins = vm.getChangeFor(21);
        Collection<Coin> expected = Arrays.asList(Coin.TEN_PENCE, Coin.FIVE_PENCE, Coin.TWO_PENCE, Coin.TWO_PENCE, Coin.TWO_PENCE);
        assertEquals(expected, coins);
    }

}
