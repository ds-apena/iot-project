package de.elektronikschule.iotproject.mqtt;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.elektronikschule.iotproject.persistence.Measurement;
import de.elektronikschule.iotproject.persistence.MeasurementRepository;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MQTTSubscriber {
   private final MeasurementRepository repo;
   private static final Logger logger = LogManager.getLogger(MQTTSubscriber.class);

   // JSON parser
   ObjectMapper om = new ObjectMapper();

   @Value("${mqtt.broker}")
   private String broker;

   @Value("${mqtt.clientId}")
   private String clientId;

   @Value("${mqtt.topic}")
   private String topic;

   @Autowired
   public MQTTSubscriber(MeasurementRepository repo) {
      this.repo = repo;
   }

   @PostConstruct
   public void subscribe() {
      try(MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence())){

         // connect options
         MqttConnectOptions options = new MqttConnectOptions();
         // Time interval for connection with MQTT server
         options.setConnectionTimeout(60);
         // Notify broker about the state of the current connection
         options.setKeepAliveInterval(60);
         // setup callback
         client.setCallback(new MqttCallback() {

            public void connectionLost(Throwable cause) {
               logger.warn(String.format("connection lost: %1s", cause));
            }

            public void messageArrived(String topic, MqttMessage message) {
               try {
                  String payload = new String(message.getPayload());
                  JsonNode node = om.readTree(payload);
                  double temperature = node.get("temperature").asDouble();
                  double pressure = node.get("pressure").asDouble();
                  double dhtHumidity = node.get("dhtHumidity").asDouble();
                  double altitude = node.get("altitude").asDouble();
                  double dhtTemperature = node.get("dhtTemperature").asDouble();

                  Measurement measurement = new Measurement();
                  measurement.setTemperature(temperature);
                  measurement.setPressure(pressure);
                  measurement.setDhtHumidity(dhtHumidity);
                  measurement.setAltitude(altitude);
                  measurement.setDhtTemperature(dhtTemperature);

                  repo.save(measurement);
               } catch (IOException e) {
                  logger.debug(e.getMessage());
               }
            }

            public void deliveryComplete(IMqttDeliveryToken token) {
               logger.info(String.format("deliveryComplete----------%1s", token.isComplete()));
            }

         });
         client.connect(options);
         client.subscribe(topic, 0);
      } catch (MqttException e) {
         logger.debug(e.getMessage());
      }
   }
}

