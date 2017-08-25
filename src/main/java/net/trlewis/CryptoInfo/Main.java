package net.trlewis.CryptoInfo;

import net.trlewis.CryptoKeyStore.AddressDatabase;
import net.trlewis.CryptoKeyStore.CryptoAddress;
import net.trlewis.CryptoKeyStore.CryptocurrencyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args)
    {
//        System.out.println("testing");
//        IBalanceGetter btcGetter = new BtcBalanceGetter();
////        double balance = btcGetter.getBalance("17A16QmavnUfCW11DAApiJxp7ARnxN5pGX");
////        System.out.println(balance);
//
//        List<String> addresses = new ArrayList<String>();
//        addresses.add("17A16QmavnUfCW11DAApiJxp7ARnxN5pGX");
//        addresses.add("197uQksKLvU8pZGWc6mnEoJ5N3BKmjcst1");
//
//        Map<String, Double> values = btcGetter.getBalances(addresses);
//        for(Map.Entry<String, Double> kvp : values.entrySet())
//        {
//            System.out.print(kvp.getKey() + " - ");
//            System.out.println(kvp.getValue());
//        }

        String databaseLoc = "jdbc:sqlite:./test.db";
        AddressDatabase.createDatabase(databaseLoc);

        List<CryptoAddress> addresses = new ArrayList<>();
        addresses.add(new CryptoAddress("1BTCsdofin32g", CryptocurrencyType.Bitcoin
            , 1.123456789, "testName"));
        addresses.add(new CryptoAddress("1BTCsdofin32g", CryptocurrencyType.Ethereum
                , 31.56789, "testEther"));



//        String blah = "blah";
//        String more = "more";
//        double d = 123.456781123234982734;
//        String ds = Double.toString(d);
//        String str = String.format("addr: %1$s %2$s %1$s %3$s", blah, more, ds);
//        System.out.println(str);
    }
}
