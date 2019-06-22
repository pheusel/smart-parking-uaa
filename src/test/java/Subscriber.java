import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

public class Subscriber {

    private static final String CONNECTION_URL = "tcp://mqtt.iot-embedded.de:1883";
    private static final String SUBSCRIPTION = "/smp/spots";
    private static final String USERNAME = "smart-parking";
    private static final String PASSWORD = "_Smart_Parking_";

    public static void main(String[] args) throws MqttException {

        MqttClient client = new MqttClient(CONNECTION_URL, MqttClient.generateClientId());
        MqttConnectOptions connOpts = setUpConnectionOptions(USERNAME, PASSWORD);

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) { }

            @Override
            public void messageArrived(String t, MqttMessage m) throws Exception {
                String time = new Timestamp(System.currentTimeMillis()).toString();
                System.out.println();
                System.out.println("***********************************************************************");
                System.out.println("Message Arrived at Time: " + time + "  Topic: " + t + "  Message: "
                        + new String(m.getPayload()));
                System.out.println("***********************************************************************");
                System.out.println();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken t) { }
        });

        client.connect(connOpts);

        client.subscribe(SUBSCRIPTION);

        MqttMessage message = new MqttMessage("Das ist noch ein Test".getBytes());
        client.publish("/smp/spots", message);

        client.disconnect();
    }

    private static MqttConnectOptions setUpConnectionOptions(String username, String password){
        MqttConnectOptions connOps = new MqttConnectOptions();
        connOps.setCleanSession(true);
        connOps.setUserName(username);
        connOps.setPassword(password.toCharArray());
        return connOps;
    }
}
