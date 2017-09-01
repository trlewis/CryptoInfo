//package net.trlewis.CryptoInfo;
//
//import org.jetbrains.annotations.Nullable;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.Map;
//
//public class BlockCypherEtherBalanceGetter extends BlockCypherBalanceGetter
//{
//    private static int WEI_DECIMAL_PLACES = 18;
//
//    @Override
//    public @Nullable BigDecimal getBalance(final String address)
//    {
//        BigDecimal balance = this.getBalance(address, CryptoType.ETH);
//        if(null == balance)
//            return null;
//        return balance.movePointLeft(WEI_DECIMAL_PLACES);
//    }
//
//    @Override
//    public @Nullable Map<String, BigDecimal> getBalances(final Iterable<String> addresses)
//    {
//        Map<String, BigDecimal> balances = this.getBalances(addresses, CryptoType.ETH);
//        if(null == balances)
//            return null;
//
//        Map<String, BigDecimal> toReturn = new HashMap<>();
//        for(Map.Entry<String, BigDecimal> kvp : balances.entrySet())
//        {
//            if(null != kvp.getValue())
//                toReturn.put(kvp.getKey(), kvp.getValue().movePointLeft(WEI_DECIMAL_PLACES));
//        }
//
//        return toReturn;
//    }
//}
