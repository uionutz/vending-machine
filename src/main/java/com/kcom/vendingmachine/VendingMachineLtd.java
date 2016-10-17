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
        Stack<Coin> result = new Stack<>();
        try{
            findSolution(result, pence);
        }catch(EmptyStackException e){
            throw new Coin.NoCoinFoundException("Insuficiend coinage for amount "+ pence, e);
        }
        return result;
    }

    private Collection<Coin> findSolution(Stack<Coin> coins, int amount) {
        if (amount == 0){
            return coins;
        }
        else {
            Optional<Coin> optionalCoin = getMaxValueCoinForValueFromInventory(amount);
            if (optionalCoin.isPresent()) {
                Coin coin = optionalCoin.get();
                Integer coinCount = coinsInventory.computeIfPresent(coin, (coin1, count) -> count - 1);
                if (coinCount <= 0) {
                    coinsInventory.entrySet().removeIf(coinIntegerEntry -> coinIntegerEntry.getKey().equals(coin));
                }
                coins.push(coin);
                return findSolution(coins, amount - coin.getDenomination());
            } else {
                Coin pop = coins.pop();
                return findSolution(coins, amount + pop.getDenomination());
            }
        }
    }

    protected Optional<Coin> getMaxValueCoinForValueFromInventory(int value) {
        return coinsInventory.keySet().stream().filter(coin -> coin.getDenomination() <= value).findFirst();
    }
}
