package com.kcom.vendingmachine;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Slf4j
@Getter
public class VendingMachineLtd {

    private Map<Coin, Integer> coinsInventory;

    public VendingMachineLtd() {
        coinsInventory = new TreeMap<>((o1, o2) -> Integer.compare(o2.getDenomination(), o1.getDenomination()));
    }

    public void loadPropsFile(String propertiesPath) throws IOException {
        Properties props = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(propertiesPath)) {
            props.load(fileInputStream);
        }

        Collections.list(props.propertyNames()).stream().map(Object::toString)
                .filter(name -> Integer.valueOf(props.getProperty(name)) > 0)
                .forEach(name -> coinsInventory.put(Coin.fromValue(Integer.valueOf(name)), Integer.valueOf(props.getProperty(name))));

    }

    public Collection<Coin> getChangeFor(int pence) {
        if (pence <= 0) {
            return Collections.emptyList();
        } else {
            Stack<Coin> result = new Stack<>();
            try {
                coinsAccumulator(result, pence);
            } catch (EmptyStackException e) {
                throw new Coin.NoCoinFoundException("Insufficient coinage for amount " + pence, e);
            }
            return result;
        }
    }

    private Collection<Coin> coinsAccumulator(Stack<Coin> coins, int amount) {
        if (amount == 0) {
            return coins;
        }
        Optional<Coin> optionalCoin = getMaxValueCoinFor(amount);
        if (optionalCoin.isPresent()) {
            Coin coin = optionalCoin.get();
            Integer coinCount = coinsInventory.computeIfPresent(coin, (coin1, count) -> count - 1);
            if (coinCount <= 0) {
                coinsInventory.entrySet().removeIf(coinIntegerEntry -> coinIntegerEntry.getKey().equals(coin));
            }
            coins.push(coin);
            return coinsAccumulator(coins, amount - coin.getDenomination());
        } else {
            Coin pop = coins.pop();
            return coinsAccumulator(coins, amount + pop.getDenomination());
        }

    }

    protected Optional<Coin> getMaxValueCoinFor(int value) {
        return coinsInventory.keySet().stream().filter(coin -> coin.getDenomination() <= value).findFirst();
    }
}
