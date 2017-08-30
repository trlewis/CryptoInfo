package net.trlewis.CryptoInfo;

import net.trlewis.CryptoKeyStore.AddressDatabase;
import net.trlewis.CryptoKeyStore.CryptoAddress;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main
{
    private static final String databaseLoc = "jdbc:sqlite:./test.db";

    public static void main(String[] args)
    {
//        System.out.println("testing");
//        IBalanceGetter btcGetter = new BlockChainInfoBtcBalanceGetter();
//        BigDecimal balance = btcGetter.getBalance("17A16QmavnUfCW11DAApiJxp7ARnxN5pGX");
//        System.out.println(balance);
//
//        List<String> addresses = new ArrayList<String>();
//        addresses.add("17A16QmavnUfCW11DAApiJxp7ARnxN5pGX");
//        addresses.add("197uQksKLvU8pZGWc6mnEoJ5N3BKmjcst1");
//
//        Map<String, Double> values = btcGetter.getBalances(addresses);
//        for(Map.Entry<String, Double> kvp : values.entrySet())
//        {
//            System.out.print(kvp.getKey() + " - ");
//            System.out.println(kvp.getValue());
//        }
//        String blah = "blah";
//        String more = "more";
//        double d = 123.456781123234982734;
//        String ds = Double.toString(d);
//        String str = String.format("addr: %1$s %2$s %1$s %3$s", blah, more, ds);
//        System.out.println(str);

//        List<CryptoAddress> addresses = getSampleAddresses();
//        for(CryptoAddress addr : addresses)
//        {
//            String insertVals = addr.getInsertValues();
//            System.out.println(insertVals);
//        }

        createSampleDatabase();

        List<CryptoAddress> addresses = readSampleDatabase();
        for(CryptoAddress addr : addresses)
            System.out.println(addr.getInsertValues());

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
