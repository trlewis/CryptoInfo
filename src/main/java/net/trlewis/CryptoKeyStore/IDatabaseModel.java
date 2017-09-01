package net.trlewis.CryptoKeyStore;

public interface IDatabaseModel
{
//    String getCreateTableString();
    int getId();
    String getInsertDescription();
    String getInsertValues();
    String getTableName();
}
