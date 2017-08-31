package net.trlewis.CryptoInfo;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

//this one seems to give quite different results for very active addresses than other
//websites
public class BlockCypherBitcoinBalanceGetter implements IBalanceGetter
{
    private static final int SATOSHI_DECIMAL_PLACES = 8;
    @Override
    public @Nullable BigDecimal getBalance(final String address)
    {
        String loc = String.format("https://api.blockcypher.com/v1/btc/main/addrs/%s/balance"
                , address);
        JsonObject json = GetRequestHelper.getJsonFromGetRequest(loc);

        if(null == json)
            return null;

        String balStr = json.get("final_balance").getAsString();
        BigDecimal bal = new BigDecimal(balStr);
        return bal.movePointLeft(SATOSHI_DECIMAL_PLACES);
    }

    @Override
    public @Nullable Map<String, BigDecimal> getBalances(final Iterable<String> addresses)
    {
        Map<String, BigDecimal> map = new HashMap<>();
        for(String a : addresses)
            map.put(a, getBalance(a));
        return map;
    }
}
