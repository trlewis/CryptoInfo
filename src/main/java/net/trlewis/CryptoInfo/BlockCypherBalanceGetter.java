// this site really doesn't look to reliable for ether. I mean, one of the final balances
// i got while testing was negative...


//package net.trlewis.CryptoInfo;
//
//import com.google.gson.JsonObject;
//import org.jetbrains.annotations.Nullable;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.Map;
//
//public abstract class BlockCypherBalanceGetter implements IBalanceGetter
//{
////    @Override
////    public @Nullable BigDecimal getBalance(String address)
////    {
////        return null;
////    }
//
//    /**
//     * Gets the "final_balance" of an address/cryptoType combo using blockcypher.com
//     * @param address The address to the the balance of.
//     * @param type The cryptocurrency type to get the balance of.
//     * @return The balance, which is *NOT* converted to the proper decimal places.
//     */
//    protected @Nullable BigDecimal getBalance(final String address, final CryptoType type)
//    {
//        String loc = String.format("https://api.blockcypher.com/v1/%1$s/main/addrs/%2$s/balance"
//            , type.name().toLowerCase(), address);
//        JsonObject json = GetRequestHelper.getJsonFromGetRequest(loc);
//        if(null == json)
//            return null;
//
//        String balStr = json.get("final_balance").getAsString();
//        return new BigDecimal(balStr);
//    }
//
////    @Override
////    public @Nullable Map<String, BigDecimal> getBalances(Iterable<String> addresses)
////    {
////        return null;
////    }
//
//    protected @Nullable Map<String, BigDecimal> getBalances(final Iterable<String> addresses
//        , final CryptoType type)
//    {
//        Map<String, BigDecimal> map = new HashMap<>();
//        for(String a : addresses)
//            map.put(a, getBalance(a, type));
//        return map;
//    }
//}
