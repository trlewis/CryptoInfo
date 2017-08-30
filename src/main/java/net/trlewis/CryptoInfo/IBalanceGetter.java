package net.trlewis.CryptoInfo;

import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.Map;

//TODO: add documentation
public interface IBalanceGetter
{
    @Nullable
    @SuppressWarnings("unused")
    BigDecimal getBalance(String address);

    @Nullable
    @SuppressWarnings("unused")
    Map<String, BigDecimal> getBalances(Iterable<String> addresses);
}
