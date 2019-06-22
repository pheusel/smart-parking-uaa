package dhbw.smapa.uaa.config;

public abstract class MqttConfig {

    protected final Integer QOS = 2;

    protected abstract void config();
}
