package net.trlewis.CryptoKeyStore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressDatabase implements AutoCloseable
{
    private final String _url;
    private final Connection _connection;

    public AddressDatabase(String url) throws SQLException {
        this._url = url;
        this._connection = DriverManager.getConnection(this._url);
    }

    private void insert(List<? extends IDatabaseModel> models)
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

    //#region static methods

    public static void createDatabase(String url)
    {
        try(Connection conn = DriverManager.getConnection(url))
        {
            if(conn != null)
            {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("driver name: " + meta.getDriverName());
                System.out.println("new database has been created: " + meta.getURL());

                createAddressTable(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createAddressTable(Connection conn) throws SQLException
    {
        String sql = new CryptoAddress().getCreateTableString();
        executeSqlNonQuery(conn, sql);
    }

    private static void executeSqlNonQuery(Connection conn, String sql) throws SQLException
    {
        Statement statement = conn.createStatement();
        statement.execute(sql);
        statement.close();
    }

    //#endregion static methods

    @Override
    public void close() throws Exception
    {
        if(!this._connection.isClosed())
            this._connection.close();
    }
}
