package net.trlewis.CryptoKeyStore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SqlNoDataSourceInspection")
public class AddressDatabase implements AutoCloseable
{
    private final Connection _connection;

    public AddressDatabase(Connection connection)
            throws SQLException {
        this._connection = connection;
    }

    //#region SELECT methods

    public List<CryptoAddress> readAllCryptoAddresses()
    {
        final String allSql = "SELECT * FROM " + CryptoAddress.TABLE_NAME;
        List<CryptoAddress> addresses = new ArrayList<>();

        try(Statement s = this._connection.createStatement())
        {
            if(s.execute(allSql))
            {
                ResultSet rs = s.getResultSet();
                while(rs.next())
                    addresses.add(CryptoAddress.readSingle(rs));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return addresses;
    }

    //#endregion SELECT methods

    public void insert(List<? extends IDatabaseModel> models)
    {
        if(models.size() == 0)
            return;

        String desc = models.get(0).getInsertDescription();
        List<String> values = new ArrayList<>();
        for(IDatabaseModel model : models)
            values.add(model.getInsertValues());
        String valStr = String.join(", ", values);
        String sql = desc + valStr;

        try(Statement s = this._connection.createStatement())
        {
            s.execute(sql);
        }
        catch (SQLException e) {
            //ignored
        }
    }


    public void createTables()
    {
        try
        {
            if(this._connection != null)
            {
//                DatabaseMetaData meta = this._connection.getMetaData();
//                System.out.println("driver label: " + meta.getDriverName());
//                System.out.println("new database has been created: " + meta.getURL());

                this.createAddressTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createAddressTable()
            throws SQLException
    {
        String sql = new CryptoAddress().getCreateTableString();
        this.executeSqlNonQuery(sql);
    }

    private void executeSqlNonQuery(String sql)
            throws SQLException
    {
        Statement statement = this._connection.createStatement();
        statement.execute(sql);
        statement.close();
    }


    @Override
    public void close() throws Exception
    {
        if(!this._connection.isClosed())
            this._connection.close();
    }
}
