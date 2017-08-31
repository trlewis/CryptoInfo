package net.trlewis.CryptoInfo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Uses the etherscan.io API to get values. Their site says to not make more than 5
 * requests per second or you'll get a 403 (forbidden) error.
 */
public class EtherscanIoEtherBalanceGetter implements IBalanceGetter
{
    //TODO: move this to another file
    private static final int WEI_DECIMAL_PLACES = 18;

    @Override
    public @Nullable BigDecimal getBalance(final String address)
    {
        String loc = String.format(
                "https://api.etherscan.io/api?module=account&action=balance&address=%s"
                , address);
        JsonObject json = GetRequestHelper.getJsonFromGetRequest(loc);
        if(!isValidResponse(json))
            return null;

        return stringBalToBigDecimal(json.get("result").getAsString());
    }

    @Override
    public @Nullable Map<String, BigDecimal> getBalances(Iterable<String> addresses)
    {
        String param = String.join(",", addresses);
        String loc = String.format(
                "https://api.etherscan.io/api?module=account&action=balancemulti&address=%s"
                , param);
        JsonObject json = GetRequestHelper.getJsonFromGetRequest(loc);
        if(!isValidResponse(json))
            return null;

        JsonArray addressArray = json.get("result").getAsJsonArray();
        Map<String, BigDecimal> balanceMap = new HashMap<>();
        for(JsonElement elem : addressArray)
        {
            JsonObject obj = elem.getAsJsonObject();
            String addr = obj.get("account").getAsString();
            BigDecimal bal = stringBalToBigDecimal(obj.get("balance").getAsString());
            if(null != addr && 0 != addr.trim().length() && null != bal)
                balanceMap.put(addr, bal);
        }

        return balanceMap;
    }

    private static BigDecimal stringBalToBigDecimal(final String balanceStr)
    {
        BigDecimal bal = new BigDecimal(balanceStr);
        return bal.movePointLeft(WEI_DECIMAL_PLACES);
    }

    private static boolean isValidResponse(JsonObject json)
    {
        if(null == json)
            return false;
        String status = json.get("status").getAsString();
        return null != status && status.equals("1");
    }
}
