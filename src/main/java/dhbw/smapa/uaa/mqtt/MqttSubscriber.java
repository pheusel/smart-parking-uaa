package dhbw.smapa.uaa.mqtt;

import com.google.gson.Gson;
import dhbw.smapa.uaa.config.MqttConfig;
import dhbw.smapa.uaa.controller.ParkingController;
import dhbw.smapa.uaa.entity.BrokerMessage;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class MqttSubscriber extends MqttConfig implements MqttCallback {

    private final String CONNECTION_URL;
    private final String USERNAME;
    private final String PASSWORD;

    @Autowired
    ParkingController parkingController;

    public MqttSubscriber(@Value("${mqtt.connection-url}") String connectionUrl, @Value("${mqtt.username}") String username, @Value("${mqtt.password}") String password) {
        this.CONNECTION_URL = connectionUrl;
        this.USERNAME = username;
        this.PASSWORD = password;

        this.config();
    }

    private MqttClient client = null;

    private static final Logger LOGGER = LoggerFactory.getLogger(MqttSubscriber.class);

    @Override
    public void connectionLost(Throwable throwable) {
    }


    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {

        Gson gson = new Gson();
        String time = new Timestamp(System.currentTimeMillis()).toString();
        BrokerMessage brokerMessage = gson.fromJson(mqttMessage.toString(), BrokerMessage.class);
        brokerMessage.setTime(time);
        parkingController.messageArrived(brokerMessage);
    }


    @Override
    public void deliveryComplete(IMqttDeliveryToken t) {
    }

    public void subscribeMessage(String subscription) {
        try {
            this.client.subscribe(subscription, QOS);
        } catch (MqttException me) {
            me.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            this.client.disconnect();
        } catch (MqttException me) {
            LOGGER.error("ERROR", me);
        }
    }

    protected void config() {

        MemoryPersistence persistence = new MemoryPersistence();
        MqttConnectOptions connOpts = setUpConnectionOptions(USERNAME, PASSWORD);

        try {
            this.client = new MqttClient(CONNECTION_URL, MqttClient.generateClientId(), persistence);
            this.client.connect(connOpts);
            this.client.setCallback(this);
        } catch (MqttException me) {
            me.printStackTrace();
        }
    }

    private static MqttConnectOptions setUpConnectionOptions(String username, String password) {
        MqttConnectOptions connOps = new MqttConnectOptions();
        connOps.setCleanSession(true);
        connOps.setUserName(username);
        connOps.setPassword(password.toCharArray());
        return connOps;
    }
}
