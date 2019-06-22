package dhbw.smapa.uaa.config;

public abstract class MqttConfig {

    protected static final String CONNECTION_URL = "tcp://mqtt.iot-embedded.de:1883";
    protected static final String SUBSCRIPTION = "/smp/spots";
    protected static final String USERNAME = "smart-parking";
    protected static final String PASSWORD = "_Smart_Parking_";

    protected final Integer QOS = 2;

    protected abstract void config();
}
