package net.trlewis.CryptoInfo;

import java.util.List;
import java.util.Map;

//TODO: add documentation
public interface IBalanceGetter
{
    double getBalance(String address);

    Map<String, Double> getBalances(Iterable<String> addresses);
}
