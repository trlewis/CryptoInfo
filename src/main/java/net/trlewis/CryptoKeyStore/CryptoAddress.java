package net.trlewis.CryptoKeyStore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CryptoAddress implements IDatabaseModel
{
    private static final String TABLE_NAME = "CryptoAddresses";

    public String address;
    public CryptocurrencyType currencyType;
    public double quantity;
    public String name;
    public int id;

    public CryptoAddress()
    {}

    public CryptoAddress(String myAddress, CryptocurrencyType myType, double myQuantity
        , String myName)
    {
        this.address = myAddress;
        this.currencyType = myType;
        this.quantity = myQuantity;
        this.name = myName;
    }

    public CryptoAddress(int myId, String myAddress, CryptocurrencyType myType, double myQuantity
            , String myName)
    {
        this(myAddress, myType, myQuantity, myName);
        this.id = myId;
    }

    @Override
    public String getInsertValues()
    {
        String quantity = Double.toString(this.quantity);
        boolean hasName = name != null && name.trim().length() != 0;
        String n = hasName ? String.format("'$s'", this.name) :"NULL";
        return String.format("('%1$s', '%2$s', %3$s, %4$s)", this.address
            , this.currencyType.name(), quantity, n);
        //read currencytype back in with CryptocurrencyType.valueOf("foo");
    }

    @Override
    public String getCreateTableString()
    {
        return "CREATE TABLE IF NOT EXISTS Addresses (\n" +
                " id integer PRIMARY KEY AUTOINCREMENT, \n" +
                " address varchar(100) NOT NULL, \n" +
                " currencyType varchar(50) NOT NULL,\n" +
                " quantity decimal(30,15), \n" +
                " name varchar(250) \n" +
                ");";
    }

    @Override
    public String getTableName()
    {
        return TABLE_NAME;
    }

    @Override
    public String getInsertDescription()
    {
        return String.format("INSERT INTO %1$s (address, currencyType" +
                        ", quantity, name) VALUES "
                , TABLE_NAME);
    }

    //for "SELECT * FROM Addresses [WHERE ...]" type queries only
    public static CryptoAddress readSingle(ResultSet rs) throws SQLException
    {
        String addr = rs.getString("address");
        CryptocurrencyType type = CryptocurrencyType.valueOf(rs.getString("currencyType"));
        double qty = rs.getDouble("quantity");
        String n = rs.getString("name");
        int i = rs.getInt("id");

        CryptoAddress address = new CryptoAddress(i, addr, type, qty, n);
//        address.currencyType = type;
//        address.address = addr;
//        address.quantity = qty;
//        address.name = n;
//        address.id = i;

        return address;
    }
}
