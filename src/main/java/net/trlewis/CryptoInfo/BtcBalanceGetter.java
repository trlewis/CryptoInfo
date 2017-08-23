package net.trlewis.CryptoInfo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

//might need to make different classes for different blockchain explorer websites
public class BtcBalanceGetter implements IBalanceGetter
{
    //TODO: put this elsewhere
    private static final double SATOSHIS_PER_BITCOIN = 100000000;

    public double getBalance(String address)
    {
        String loc = String.format("https://blockchain.info/rawaddr/%s?limit=0", address);
        JsonObject json = GetRequestHelper.getJsonFromGetRequest(loc);

        if(json != null)
        {
            String balance = json.get("final_balance").getAsString();
            return Double.parseDouble(balance) / SATOSHIS_PER_BITCOIN;
        }

        return -1; //crypto addresses shouldn't be able to have a negative balance, right?
    }

    public Map<String, Double> getBalances(Iterable<String> addresses)
    {
        String addrParam = String.join("|", addresses);
        String url = String.format("https://blockchain.info/multiaddr?active=%s", addrParam);
        JsonObject json = GetRequestHelper.getJsonFromGetRequest(url);

        if(json == null)
            return null;

        //TODO: make sure they're all in the same order as the input addresses.
        JsonArray addrs = json.get("addresses").getAsJsonArray();
        Map<String, Double> balances = new HashMap<>();
        for(JsonElement elem : addrs)
        {
            JsonObject obj = elem.getAsJsonObject();
            String thisAddr = obj.get("address").getAsString();
            String balStr = obj.get("final_balance").getAsString();
            Double bal = Double.parseDouble(balStr) / SATOSHIS_PER_BITCOIN;

            balances.put(thisAddr, bal);
        }

        return balances;
    }
}
