package net.trlewis.CryptoInfo;

import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ExplorerDashBalanceGetter implements IBalanceGetter
{
    @Override
    public @Nullable BigDecimal getBalance(String address) {
        final String url = "https://explorer.dash.org/chain/Dash/q/addressbalance/" + address;
        String val = GetRequestHelper.getRawGetRequest(url);
        if(null != val && val.length() > 0)
            return new BigDecimal(val);
        return null;
    }

    @Override
    public @Nullable Map<String, BigDecimal> getBalances(Iterable<String> addresses)
    {
        // doesn't seem to be a way for this site to take multiple addresses
        Map<String, BigDecimal> map = new HashMap<>();
        for(String a : addresses)
            map.put(a, getBalance(a));
        return map;
    }
}
