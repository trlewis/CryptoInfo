package net.trlewis.CryptoKeyStore;

public interface IDatabaseModel
{
    String getTableName();
    String getInsertDescription();
    String getInsertValues();
    String getCreateTableString();
}
