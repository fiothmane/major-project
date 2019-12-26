package com.emse.spring.faircorp.mqtt;

import org.eclipse.paho.client.mqttv3.*;

/* Inspired from: https://gist.github.com/m2mIO-gister/5275324 */
public class PhilipsHueMqttClient implements MqttCallback {
    private String broker = "tcp://192.168.1.3:1883";
    private String clientId = "walid-ouchtiti";

    private MqttClient mqttClient;
    private MqttConnectOptions mqttConnectOptions;

    String publishTopic = "hue/control";

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
        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setKeepAliveInterval(30);
        mqttConnectOptions.setUserName("walid");
        mqttConnectOptions.setPassword("walid".toCharArray());

        MqttTopic mqttTopic = mqttClient.getTopic(publishTopic);

        try {
            mqttClient = new MqttClient(broker, clientId);
            mqttClient.setCallback(this);
            mqttClient.connect(mqttConnectOptions);

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

    }
}
