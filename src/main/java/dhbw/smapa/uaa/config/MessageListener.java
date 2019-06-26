package dhbw.smapa.uaa.config;

import dhbw.smapa.uaa.mqtt.MqttSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListener implements Runnable {

    private MqttSubscriber mqttSubscriber;

    @Autowired
    public MessageListener(MqttSubscriber mqttSubscriber) {
        this.mqttSubscriber = mqttSubscriber;
    }

    private static final String SUBSCRIPTION = "/smp/spots";

    @Override
    public void run() {
        while (true) mqttSubscriber.subscribeMessage(SUBSCRIPTION);
    }
}
