package nl.laaksoft.obd;

public interface IObdConnection
{
    void startObdConnection() throws Exception;

    void stopObdConnection();

    void updateData(VehicleData m_ObdData);

}
