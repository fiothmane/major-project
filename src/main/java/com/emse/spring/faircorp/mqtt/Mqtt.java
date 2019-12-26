package com.emse.spring.faircorp.mqtt;

import org.eclipse.paho.client.mqttv3.*;

/* Inspired from: https://gist.github.com/m2mIO-gister/5275324 */
public class Mqtt implements MqttCallback {
    private String broker = "tcp://tailor.cloudmqtt.com:12491";
    private String clientId = "walid-ouchtiti";

    private MqttClient mqttClient;

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("Message arrived");
        System.out.println("Topic: " + topic);
        System.out.println("Message: " + message.toString());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    public void mqttClient(String publishMessage) {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setKeepAliveInterval(30);
        mqttConnectOptions.setUserName("vclydlgd");
        mqttConnectOptions.setPassword("TpzXBXjXI7yD".toCharArray());

        if (mqttClient != null) {
            if (mqttClient.isConnected()) {
                try {
                    mqttClient.disconnect();
                    mqttClient = null;
                    Thread.sleep(5000);
                } catch (MqttException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        MqttTopic mqttTopic = null;
        try {
            mqttClient = new MqttClient(broker, clientId);
            mqttClient.setCallback(this);
            mqttClient.connect(mqttConnectOptions);

            mqttTopic = mqttClient.getTopic("hue/control");

        } catch (MqttException e) {
            e.printStackTrace();
        }

        System.out.println("Connected to " + broker);

        /* Publish to publish topic
        * this will send required info to arduino to control the philips hue bulb */
        int pubQos = 0;
        MqttMessage mqttMessage = new MqttMessage(publishMessage.getBytes());
        mqttMessage.setQos(pubQos);
        mqttMessage.setRetained(false);

        /* Publish the message */
        System.out.println("Publishing the message: " + publishMessage + " to topic: " + mqttTopic);
        MqttDeliveryToken token = null;

        try {
            token = mqttTopic.publish(mqttMessage);
            token.waitForCompletion();
            Thread.sleep(100);
        } catch (MqttException | InterruptedException e) {
            e.printStackTrace();
        }

        if (mqttClient != null) {
            if (mqttClient.isConnected()) {
                try {
                    System.out.println("DISCONNECTING");
                    mqttClient.disconnect();
                    mqttClient = null;
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* Subscribe to topic (only for testing) */
    public void subscribe() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setKeepAliveInterval(30);
        mqttConnectOptions.setUserName("vclydlgd");
        mqttConnectOptions.setPassword("TpzXBXjXI7yD".toCharArray());

        try {
            mqttClient = new MqttClient(broker, "client2");
            mqttClient.setCallback(this);
            mqttClient.connect(mqttConnectOptions);

        } catch (MqttException e) {
            e.printStackTrace();
        }

        try {
            int subQos = 0;
            mqttClient.subscribe("hue/control", subQos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
