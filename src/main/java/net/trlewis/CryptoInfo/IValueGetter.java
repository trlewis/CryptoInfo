package net.trlewis.CryptoInfo;

import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Gets the value of various cryptocurrencies (currently in USD only). Implementors
 * must document if there are currencies it does not support.
 */
public interface IValueGetter
{
    /**
     * Get the USD value of the supplied type. A negative value meant an error occurred.
     */
    Double getValue(final CryptoType type);

    /**
     * Get the USD values of the supplied types. May return null if there was a problem with
     * the website call. Individual entries that are negative means there was a problem with
     * getting the value of that particular cryptocurrency.
     */
    @Nullable
    Map<CryptoType, Double> getValues(Iterable<CryptoType> types);
}
