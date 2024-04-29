package de.elektronikschule.iotproject.controller;

import de.elektronikschule.iotproject.persistence.Measurement;
import de.elektronikschule.iotproject.persistence.MeasurementDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest API Controller to fetch values from the application's database.
 */
@Controller
@RequestMapping("/iot")
public class MeasurementController {

   private static final Logger logger = LogManager.getLogger(MeasurementController.class);

   private final MeasurementDAO repository;

   @Autowired
   public MeasurementController(MeasurementDAO measurementDAO) {
      this.repository = measurementDAO;
   }

   @GetMapping( "/showMeasurements")
   public String showMeasurements(Model model) {
      List<Measurement> measurements = repository.listLastMeasurements();
      String[] labels = measurements.stream().map(m -> m.getTimestamp().toString()).toArray(String[]::new);

      // Altitude - Chart 1
      Double[] altitude = measurements.stream().map(Measurement::getAltitude).toArray(Double[]::new);
      Double[] dhtHumidity = measurements.stream().map(Measurement::getDhtHumidity).toArray(Double[]::new);
      Double[] dhtTemperature = measurements.stream().map(Measurement::getDhtTemperature).toArray(Double[]::new);
      Double[] pressure = measurements.stream().map(Measurement::getPressure).toArray(Double[]::new);
      Double[] temperature = measurements.stream().map(Measurement::getTemperature).toArray(Double[]::new);

      // Add common labels to each chart
      model.addAttribute("timestamp", labels);

      // Add individual label and chart data
      String label1 = "Altitude chart";
      model.addAttribute("label1", label1);
      model.addAttribute("data1", altitude);

      String label2 = "DHT Humidity chart";
      model.addAttribute("label2", label2);
      model.addAttribute("data2", dhtHumidity);

      String label3 = "DHT Temperature chart";
      model.addAttribute("label3", label3);
      model.addAttribute("data3", dhtTemperature);

      String label4 = "Pressure chart";
      model.addAttribute("label4", label4);
      model.addAttribute("data4", pressure);

      String label5 = "Temperature chart";
      model.addAttribute("label5", label5);
      model.addAttribute("data5", temperature);

      return "linechart";
   }
}
