package net.trlewis.CryptoInfo;

import net.trlewis.CryptoKeyStore.AddressDatabase;
import net.trlewis.CryptoKeyStore.CryptoAddress;

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
//        IBalanceGetter btcGetter = new BtcBalanceGetter();
////        double balance = btcGetter.getBalance("17A16QmavnUfCW11DAApiJxp7ARnxN5pGX");
////        System.out.println(balance);
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
        List<CryptoAddress> addresses = readSampleDatabase();
        for(CryptoAddress addr : addresses)
            System.out.println(addr.getInsertValues());
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
        Connection connection = DriverManager.getConnection(databaseLoc);
        return connection;
    }

    private static List<CryptoAddress> getSampleAddresses()
    {
        List<CryptoAddress> addresses = new ArrayList<>();
        addresses.add(new CryptoAddress("1BTCsdofin32g", CryptocurrencyType.BTC
            , 1.123456789, "testName"));
        addresses.add(new CryptoAddress("1BTCsdofin32gsdf", CryptocurrencyType.ETH
                , 31.56789, "testEther"));
        return addresses;
    }

}
