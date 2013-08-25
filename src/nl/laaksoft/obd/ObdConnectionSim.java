package nl.laaksoft.obd;

import nl.laaksoft.obd.VehicleData.Gear;

public class ObdConnectionSim implements IObdConnection
{
    private boolean m_Acc;
    private Gear m_SimGear;

    public ObdConnectionSim()
    {
        m_Acc = true;
        m_SimGear = Gear.GEAR0;
    }

    @Override
    public void startObdConnection() throws Exception
    {
    }

    @Override
    public void stopObdConnection()
    {
    }

    @Override
    public void updateData(VehicleData m_ObdData)
    {
        if (m_Acc)
        {
            if (m_SimGear == Gear.GEAR0)
            {
                m_ObdData.m_VehicleSpeed = 0;
                m_ObdData.m_EngineLoad = 50;
                m_ObdData.m_EngineRpm += 25;
                if (m_ObdData.m_EngineRpm > 800)
                {
                    m_SimGear = Gear.GEAR1;
                    m_ObdData.m_VehicleSpeed = 5;
                }
            }
            else
            {
                m_ObdData.m_VehicleSpeed += 0.5;
                m_ObdData.m_EngineLoad = 90;
                m_ObdData.m_EngineRpm = m_ObdData.m_VehicleSpeed
                        * m_ObdData.m_GearRatios.get(m_SimGear);

                if (m_ObdData.m_EngineRpm > 2500 && m_SimGear == Gear.GEAR1)
                    m_SimGear = Gear.GEAR2;
                else if (m_ObdData.m_EngineRpm > 2300 && m_SimGear == Gear.GEAR2)
                    m_SimGear = Gear.GEAR3;
                else if (m_ObdData.m_EngineRpm > 2200 && m_SimGear == Gear.GEAR3)
                    m_SimGear = Gear.GEAR4;
                else if (m_ObdData.m_EngineRpm > 2100 && m_SimGear == Gear.GEAR4)
                    m_SimGear = Gear.GEAR5;
                else if (m_ObdData.m_EngineRpm > 3000 && m_SimGear == Gear.GEAR5)
                    m_Acc = false;
            }
        }
        else
        {
            m_ObdData.m_VehicleSpeed -= 1;
            m_ObdData.m_EngineLoad = 0;
            m_ObdData.m_EngineRpm = m_ObdData.m_VehicleSpeed
                    * m_ObdData.m_GearRatios.get(m_SimGear);

            if (m_ObdData.m_EngineRpm < 1200 && m_SimGear == Gear.GEAR5)
                m_SimGear = Gear.GEAR4;
            else if (m_ObdData.m_EngineRpm < 1200 && m_SimGear == Gear.GEAR4)
                m_SimGear = Gear.GEAR3;
            else if (m_ObdData.m_EngineRpm < 1200 && m_SimGear == Gear.GEAR3)
                m_SimGear = Gear.GEAR2;
            else if (m_ObdData.m_EngineRpm < 1200 && m_SimGear == Gear.GEAR2)
                m_SimGear = Gear.GEAR1;
            else if (m_ObdData.m_EngineRpm < 800 && m_SimGear == Gear.GEAR1)
                m_Acc = true;
        }
    }
}
