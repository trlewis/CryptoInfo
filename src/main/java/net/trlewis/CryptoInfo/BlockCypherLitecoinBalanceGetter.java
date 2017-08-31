package net.trlewis.CryptoInfo;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class BlockCypherLitecoinBalanceGetter implements IBalanceGetter
{
    //what is their smallest unit name?
    private static final int SATOSHIS_PER_LITECOIN = 8;

    @Override
    public @Nullable BigDecimal getBalance(final String address)
    {
        String loc = String.format("https://api.blockcypher.com/v1/ltc/main/addrs/%s/balance"
            , address);
        JsonObject json = GetRequestHelper.getJsonFromGetRequest(loc);

        if(null == json)
            return null;

        String balStr = json.get("final_balance").getAsString();
        BigDecimal bd = new BigDecimal(balStr);
        return bd.movePointLeft(SATOSHIS_PER_LITECOIN);
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
