package net.trlewis.CryptoInfo;

import net.trlewis.CryptoKeyStore.AddressDatabase;
import net.trlewis.CryptoKeyStore.CryptoAddress;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main
{
    private static final String databaseLoc = "jdbc:sqlite:./test.db";

    public static void main(String[] args)
    {
//        IBalanceGetter btcGetter = new BlockCypherBitcoinBalanceGetter();
//        BigDecimal balance = btcGetter.getBalance("1KdGZGAXK6yrJCSJ5Wrh9BB4SEKarLBq2U");
//        System.out.println(balance);

        List<CryptoType> types = new ArrayList<>();
        types.add(CryptoType.BTC);
        types.add(CryptoType.ETH);
        types.add(CryptoType.DASH);
        types.add(CryptoType.DOGE);
        types.add(CryptoType.LTC);

        IValueGetter valueGetter = new CryptoCompareValueGetter();
        Map<CryptoType, Double> values = valueGetter.getValues(types);
        for(Map.Entry<CryptoType, Double> kvp : values.entrySet())
        {
            String line = String.format("1 %1$s = %2$s USD"
                    , kvp.getKey().name(), kvp.getValue());
            System.out.println(line);
        }

        System.out.println("------");

        List<String> addresses = new ArrayList<>();
        addresses.add("0xde0b295669a9fd93d5f28d9ec85e40f4cb697bae");
        addresses.add("0x5eD8Cee6b63b1c6AFce3AD7c92f4fD7E1B8fAd9F");
        IBalanceGetter ethGetter = new EtherscanIoEtherBalanceGetter();
        Map<String, BigDecimal> ethValues = ethGetter.getBalances(addresses);
        BigDecimal ethToUsd = new BigDecimal(values.get(CryptoType.ETH));
        for(Map.Entry<String, BigDecimal> kvp : ethValues.entrySet())
        {
            BigDecimal usdVal = kvp.getValue().multiply(ethToUsd, MathContext.DECIMAL64);
            String line = String.format("%1$s : %2$s ETH = %3$s USD", kvp.getKey()
                , kvp.getValue(), usdVal);
            System.out.println(line);
        }

//        createSampleDatabase();

//        List<CryptoAddress> addresses = readSampleDatabase();
//        for(CryptoAddress addr : addresses)
//            System.out.println(addr.getInsertValues());

//        for(CryptoAddress addr : getSampleAddresses())
//        {
//            String s = String.format("'%1$s", addr.quantity);
//            System.out.println(s);
//            System.out.println(addr.quantity.toString());
//        }
    }

    private static void createSampleDatabase()
    {
        try(Connection connection = DriverManager.getConnection(databaseLoc))
        {
            AddressDatabase db = new AddressDatabase(connection);
            db.createTables();
            List<CryptoAddress> sampleAddresses = getSampleAddresses();
            db.insert(sampleAddresses);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private static List<CryptoAddress> readSampleDatabase()
    {
        List<CryptoAddress> addresses = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(databaseLoc))
        {
            AddressDatabase db = new AddressDatabase(connection);
            addresses = db.readAllCryptoAddresses();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return addresses;
    }

    private static Connection tryCreatingDatabase()
            throws SQLException
    {
        String databaseLoc = "jdbc:sqlite:./test.db";
        return DriverManager.getConnection(databaseLoc);
    }

    private static List<CryptoAddress> getSampleAddresses()
    {
        List<CryptoAddress> addresses = new ArrayList<>();
        addresses.add(new CryptoAddress("1BTCsdofin32g", CryptoType.BTC
            , new BigDecimal("1234567890.12345678900987654321"), "testName"));
        addresses.add(new CryptoAddress("1BTCsdofin32gsdf", CryptoType.ETH
                , new BigDecimal(31.458585), "testEther"));
        return addresses;
    }

}
