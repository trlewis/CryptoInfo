//blockexplorer.com was returning 403 (forbidden) requests from this getter

//package net.trlewis.CryptoInfo;
//
//import com.google.gson.JsonObject;
//import org.jetbrains.annotations.Nullable;
//
//import java.math.BigDecimal;
//import java.util.Map;
//
//public class BlockExplorerBitcoinBalanceGetter implements IBalanceGetter
//{
//    private static final int SATOSHI_DECIMAL_PLACES = 8;
//
//    @Override
//    public @Nullable BigDecimal getBalance(final String address)
//    {
//        String loc = String.format("https://blockexplorer.com/api/addr/%s/balance", address);
//        String raw = GetRequestHelper.getRawGetRequest(loc);
//        if(null == raw || 0 == raw.length())
//            return null;
//        BigDecimal dec = new BigDecimal(raw);
//        return dec.movePointLeft(SATOSHI_DECIMAL_PLACES);
//    }
//
//    @Override
//    public @Nullable Map<String, BigDecimal> getBalances(final Iterable<String> addresses)
//    {
//        return null;
//    }
//}
