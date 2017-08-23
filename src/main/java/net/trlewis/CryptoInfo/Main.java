package net.trlewis.CryptoInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args)
    {
        System.out.println("testing");
        IBalanceGetter btcGetter = new BtcBalanceGetter();
//        double balance = btcGetter.getBalance("17A16QmavnUfCW11DAApiJxp7ARnxN5pGX");
//        System.out.println(balance);

        List<String> addresses = new ArrayList<String>();
        addresses.add("17A16QmavnUfCW11DAApiJxp7ARnxN5pGX");
        addresses.add("197uQksKLvU8pZGWc6mnEoJ5N3BKmjcst1");

        Map<String, Double> values = btcGetter.getBalances(addresses);
        for(Map.Entry<String, Double> kvp : values.entrySet())
        {
            System.out.print(kvp.getKey() + " - ");
            System.out.println(kvp.getValue());
        }
    }
}
