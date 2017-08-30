package net.trlewis.CryptoKeyStore;

import net.trlewis.CryptoInfo.CryptoType;
import org.intellij.lang.annotations.Language;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlDialectInspection"})
public class CryptoAddress implements IDatabaseModel
{
    public static final String TABLE_NAME = "CryptoAddresses";

    public String address;
    public CryptoType currencyType;
    public BigDecimal quantity;
    public String label;
    public int id;

    CryptoAddress() {}

    public CryptoAddress(String myAddress, CryptoType myType
            , BigDecimal myQuantity, String myName)
    {
        this.address = myAddress;
        this.currencyType = myType;
        this.quantity = myQuantity;
        this.label = myName;
    }

    private CryptoAddress(int myId, String myAddress, CryptoType myType
            , BigDecimal myQuantity, String myName)
    {
        this(myAddress, myType, myQuantity, myName);
        this.id = myId;
    }

    @Override
    public String getInsertValues()
    {
        String quantity = this.quantity.toString();
        boolean hasName = label != null && label.trim().length() != 0;
        String n = hasName ? String.format("'%s'", this.label) :"NULL";
        return String.format("('%1$s', '%2$s', '%3$s', %4$s)", this.address
            , this.currencyType.name(), quantity, n);
        //read currencytype back in with CryptoType.valueOf("foo");
    }

    @Override
    public String getCreateTableString()
    {
        @Language("SQL")
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n" +
                " id integer PRIMARY KEY AUTOINCREMENT, \n" +
                " address varchar(100) NOT NULL, \n" +
                " currencyType varchar(50) NOT NULL,\n" +
                " quantity varchar(250), \n" +
                " label varchar(50) \n" +
                ");";
        return sql;
    }

    @Override
    public String getInsertDescription()
    {
        @Language("SQL")
        String sql = "INSERT INTO " + TABLE_NAME 
                + " (address, currencyType, quantity, label) VALUES ";
        return sql;
    }

    //for "SELECT * FROM Addresses [WHERE ...]" type queries only
    static CryptoAddress readSingle(ResultSet rs) throws SQLException
    {
        String addr = rs.getString("address");
        CryptoType type = CryptoType.valueOf(rs.getString("currencyType"));
        String dbQuantity = rs.getString("quantity");
        BigDecimal qty = new BigDecimal(dbQuantity);
        String n = rs.getString("label");
        int i = rs.getInt("id");

        return new CryptoAddress(i, addr, type, qty, n);
    }
}
