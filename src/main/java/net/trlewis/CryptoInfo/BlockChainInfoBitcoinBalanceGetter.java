package net.trlewis.CryptoInfo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class BlockChainInfoBitcoinBalanceGetter implements IBalanceGetter
{
    //TODO: put this elsewhere
//    private static final double SATOSHIS_PER_BITCOIN = 100000000;
    private static final int SATOSHI_DECIMAL_PLACES = 8;

    public BigDecimal getBalance(final String address)
    {
        String loc = String.format("https://blockchain.info/rawaddr/%s?limit=0", address);
        JsonObject json = GetRequestHelper.getJsonFromGetRequest(loc);

        if(json != null)
        {
            String balanceStr = json.get("final_balance").getAsString();
            return getValueFromString(balanceStr);
        }

        return null;
    }

    public Map<String, BigDecimal> getBalances(final Iterable<String> addresses)
    {
        String addrParam = String.join("|", addresses);
        String url = String.format("https://blockchain.info/multiaddr?active=%s", addrParam);
        JsonObject json = GetRequestHelper.getJsonFromGetRequest(url);

        if(json == null)
            return null;

        JsonArray addrs = json.get("addresses").getAsJsonArray();
        Map<String, BigDecimal> balances = new HashMap<>();
        for(JsonElement elem : addrs)
        {
            JsonObject obj = elem.getAsJsonObject();
            String thisAddr = obj.get("address").getAsString();
            String balStr = obj.get("final_balance").getAsString();
            BigDecimal bal = getValueFromString(balStr);
            balances.put(thisAddr, bal);
        }

        return balances;
    }

    private static BigDecimal getValueFromString(final String stringSatoshis)
    {
        BigDecimal satoshis = new BigDecimal(stringSatoshis);
        return satoshis.movePointLeft(SATOSHI_DECIMAL_PLACES);
    }
}
