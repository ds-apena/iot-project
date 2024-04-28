package de.elektronikschule.iotproject.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Measurement {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Temporal(TemporalType.TIMESTAMP)
   @CreationTimestamp
   private LocalDateTime timestamp;

   private double temperature;

   private double pressure;

   private double dhtHumidity;

   private double altitude;

   private double dhtTemperature;

}
