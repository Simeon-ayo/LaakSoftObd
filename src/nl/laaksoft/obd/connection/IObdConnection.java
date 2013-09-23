package nl.laaksoft.obd.connection;

public interface IObdConnection
{
    void startObdConnection() throws Exception;

    void stopObdConnection();

    void updateData(VehicleData m_ObdData);

}
