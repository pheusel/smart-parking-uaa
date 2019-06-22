package dhbw.smapa.uaa.mqtt;

import dhbw.smapa.uaa.config.MqttConfig;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class MqttSubscriber extends MqttConfig implements MqttCallback  {






    private MqttClient client = null;
    private MqttConnectOptions connOpts = null;
    private MemoryPersistence persistence = null;



    private static final Logger LOGGER = LoggerFactory.getLogger(MqttSubscriber.class);

    public MqttSubscriber() {
        this.config();
    }


    @Override
    public void connectionLost(Throwable throwable) {
    }


    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

        String time = new Timestamp(System.currentTimeMillis()).toString();
        System.out.println();
        System.out.println("***********************************************************************");
        System.out.println("Message Arrived at Time: " + time + "  Topic: " + topic + "  Message: "
                + new String(message.getPayload()));
        System.out.println("***********************************************************************");
        System.out.println();
    }


    @Override
    public void deliveryComplete(IMqttDeliveryToken t) {
    }

    public void subscribeMessage() {
        try {
            this.client.subscribe(SUBSCRIPTION, QOS);
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

        this.persistence = new MemoryPersistence();
        this.connOpts = setUpConnectionOptions(USERNAME, PASSWORD);


        try {
            this.client = new MqttClient(CONNECTION_URL, MqttClient.generateClientId(), persistence);
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
    }
}
