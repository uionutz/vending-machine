package com.kcom.vendingmachine;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Slf4j
public class VendingMachineLtd {

    private Map<Coin, Integer> coinsInventory;

    public VendingMachineLtd() {
        coinsInventory = new TreeMap<>((o1, o2) -> Integer.compare(o2.getDenomination(), o1.getDenomination()));
    }

    public Map<Coin, Integer> getCoinsInventory() {
        return coinsInventory;
    }

    public void loadPropsFile(String propertiesPath) {
        Properties props = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/" + propertiesPath)) {
            props.load(fileInputStream);
        } catch (IOException e) {
            log.error("error opening file {}", propertiesPath, e);
        }

        Collections.list(props.propertyNames()).stream().map(Object::toString)
                .filter(name -> Integer.valueOf(props.getProperty(name)) > 0)
                .forEach(name -> coinsInventory.put(Coin.fromValue(Integer.valueOf(name)), Integer.valueOf(props.getProperty(name))));

    }

    public Collection<Coin> getOptimalChangeFor(int pence) {
        if (pence <= 0) {
            return Collections.emptyList();
        } else {
            return getOptimalChangeForPositiveAmounts(pence);
        }
    }

    protected Collection<Coin> getOptimalChangeForPositiveAmounts(int pence) {
        Collection<Coin> result = new ArrayList<>();
        int amount = pence;
        while (amount > 0) {
            Coin coin = getMaxValueCoinForValueFromInventory(amount);
            Integer value = coinsInventory.computeIfPresent(coin, (coin1, count) -> count - 1);
            if (value <= 0) {
                coinsInventory.entrySet().removeIf(coinIntegerEntry -> coinIntegerEntry.getKey().equals(coin));
            }
            amount = amount - coin.getDenomination();

            result.add(coin);
        }
        return result;
    }

    protected Coin getMaxValueCoinForValueFromInventory(int value) {
        Optional<Coin> first = coinsInventory.keySet().stream().filter(coin -> coin.getDenomination() <= value).findFirst();
        if (first.isPresent()) {
            return first.get();
        } else {
            throw new Coin.NoCoinFoundException("No coin found for this amount: " + value);
        }
    }
}
