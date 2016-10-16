package com.kcom.vendingmachine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.list(props.propertyNames()).stream().map(Object::toString)
                .filter(name -> Integer.valueOf(props.getProperty(name)) > 0)
                .forEach(name -> coinsInventory.put(Coin.fromValue(Integer.valueOf(name)), Integer.valueOf(props.getProperty(name))));

    }

    public Collection<Coin> getOptimalChangeFor(int pence) {
        if (pence <= 0) return Collections.EMPTY_LIST;
        else return getOptimalChangeForPositiveAmounts(pence);
    }

    protected Collection<Coin> getOptimalChangeForPositiveAmounts(int pence) {
        Collection<Coin> result = new ArrayList<>();
        int amount = pence;
        while (amount > 0) {
            Coin coin = getMaxValueCoinForValueFromInventory(amount);
            coinsInventory.computeIfPresent(coin, (coin1, integer) -> integer - coin.getDenomination());
            if (coinsInventory.get(coin) <= 0) {
                coinsInventory.entrySet().removeIf(coinIntegerEntry -> coinIntegerEntry.getKey().equals(coin));
            }
            amount = amount - coin.getDenomination();

            result.add(coin);
        }
        return result;
    }

    protected Coin getMaxValueCoinForValueFromInventory(int value) {
        List<Coin> coins = coinsInventory.keySet().stream().filter(coin -> coin.getDenomination() <= value).collect(Collectors.toList());
        if (coins.isEmpty())
            throw new Coin.NoCoinFoundException("No coin found for this amount: " + value);
        else
            return coins.stream().findFirst().get();
    }
}
