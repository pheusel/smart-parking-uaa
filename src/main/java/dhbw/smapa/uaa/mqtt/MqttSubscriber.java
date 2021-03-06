package dhbw.smapa.uaa.mqtt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

@Component
public class MqttSubscriber extends MqttConfig implements MqttCallback {

    /*private final String CONNECTION_URL;
    private final String USERNAME;
    private final String PASSWORD;

    private ParkingController parkingController;

    @Autowired
    public MqttSubscriber(@Value("${mqtt.connection-url}") String connectionUrl, @Value("${mqtt.username}") String username, @Value("${mqtt.password}") String password, ParkingController parkingController) {
        this.CONNECTION_URL = connectionUrl;
        this.USERNAME = username;
        this.PASSWORD = password;

        this.parkingController = parkingController;

        this.config();
    }*/

    private final String CONNECTION_URL;

    private ParkingController parkingController;

    @Autowired
    public MqttSubscriber(@Value("${mqtt.connection-url}") String connectionUrl, ParkingController parkingController) {
        this.CONNECTION_URL = connectionUrl;

        this.parkingController = parkingController;

        this.config();
    }


    private MqttClient client = null;
    private MemoryPersistence persistence = null;
    private MqttConnectOptions connOpts = null;

    private static final Logger LOGGER = LoggerFactory.getLogger(MqttSubscriber.class);

    @Override
    public void connectionLost(Throwable cause) {

        this.persistence = new MemoryPersistence();
        //this.connOpts = setUpConnectionOptions(USERNAME, PASSWORD);

        try {
            this.client = new MqttClient(CONNECTION_URL, MqttClient.generateClientId(), persistence);
            this.client.connect(connOpts);
            this.client.setCallback(this);
        } catch (MqttException me) {
            me.printStackTrace();
        }
    }


    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();
        BrokerMessage brokerMessage = gson.fromJson(mqttMessage.toString(), BrokerMessage.class);
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
            System.exit(1);
        }
    }

    public void disconnect() {
        try {
            this.client.disconnect();
        } catch (MqttException me) {
            LOGGER.error("ERROR", me);
        }
    }

    /*protected void config() {

        this.persistence = new MemoryPersistence();
        this.connOpts = setUpConnectionOptions(USERNAME, PASSWORD);

        try {
            this.client = new MqttClient(CONNECTION_URL, MqttClient.generateClientId(), this.persistence);
            this.client.connect(this.connOpts);
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
    }*/

    protected void config() {

        this.persistence = new MemoryPersistence();
        this.connOpts = setUpConnectionOptions();

        try {
            this.client = new MqttClient(CONNECTION_URL, MqttClient.generateClientId(), this.persistence);
            this.client.connect(this.connOpts);
            this.client.setCallback(this);
        } catch (MqttException me) {
            me.printStackTrace();
        }
    }

    private static MqttConnectOptions setUpConnectionOptions() {
        MqttConnectOptions connOps = new MqttConnectOptions();
        connOps.setCleanSession(true);
        return connOps;
    }
}
