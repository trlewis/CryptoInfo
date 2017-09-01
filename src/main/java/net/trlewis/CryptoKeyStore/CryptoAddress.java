package net.trlewis.CryptoKeyStore;

import net.trlewis.CryptoInfo.CryptoType;
import org.intellij.lang.annotations.Language;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * An immutable representation of a particular address of a particular cryptocurrency, with
 * a particular balance and name.
 */
@SuppressWarnings({"SqlNoDataSourceInspection", "SqlDialectInspection"})
public class CryptoAddress implements IDatabaseModel
{
    public static final String TABLE_NAME = "CryptoAddresses";

    private final String _address;
    private final CryptoType _cryptoType;
    private final BigDecimal _balance;
    private final String _label;
    private int _id;

    public CryptoAddress(String myAddress, CryptoType myType
            , BigDecimal myQuantity, String myName)
    {
        this._address = myAddress;
        this._cryptoType = myType;
        this._balance = myQuantity;
        this._label = myName;
    }

    private CryptoAddress(int myId, String myAddress, CryptoType myType
            , BigDecimal myQuantity, String myName)
    {
        this(myAddress, myType, myQuantity, myName);
        this._id = myId;
    }

    //#region getters

    @SuppressWarnings("unused")
    public String getAddress() { return this._address; }

    @SuppressWarnings("unused")
    public CryptoType getCryptoType() { return this._cryptoType; }

    @SuppressWarnings("unused")
    public BigDecimal getBalance() { return _balance; }

    @SuppressWarnings("unused")
    public String getLabel() { return this._label; }

    @Override
    public int getId() { return this._id; }

    //#endregion getters

    @Override
    public String getInsertValues()
    {
        String quantity = this._balance.toString();
        boolean hasName = _label != null && _label.trim().length() != 0;
        String n = hasName ? String.format("'%s'", this._label) :"NULL";
        return String.format("('%1$s', '%2$s', '%3$s', %4$s)", this._address
            , this._cryptoType.name(), quantity, n);
        //read currencytype back in with CryptoType.valueOf("foo");
    }

    @Override
    public String getTableName() { return TABLE_NAME; }

    static String getCreateTableString()
    {
        @Language("SQL")
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (\n" +
                " id integer PRIMARY KEY AUTOINCREMENT, \n" +
                " address varchar(100) NOT NULL, \n" +
                " cryptoType varchar(50) NOT NULL,\n" +
                " balance varchar(250), \n" +
                " label varchar(50) \n" +
                ");";
        return sql;
    }

    @Override
    public String getInsertDescription()
    {
        @Language("SQL")
        String sql = "INSERT INTO " + TABLE_NAME 
                + " (address, currencyType, balance, label) VALUES ";
        return sql;
    }

    //for "SELECT * FROM Addresses [WHERE ...]" type queries only
    static CryptoAddress readSingle(ResultSet rs) throws SQLException
    {
        String addr = rs.getString("address");
        CryptoType type = CryptoType.valueOf(rs.getString("cryptoType"));
        String dbQuantity = rs.getString("balance");
        BigDecimal qty = new BigDecimal(dbQuantity);
        String n = rs.getString("label");
        int i = rs.getInt("id");

        return new CryptoAddress(i, addr, type, qty, n);
    }
}
