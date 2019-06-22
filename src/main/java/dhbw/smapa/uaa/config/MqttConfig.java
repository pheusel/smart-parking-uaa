package dhbw.smapa.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class MqttConfig {

    protected final Integer QOS = 2;

    protected abstract void config();
}
