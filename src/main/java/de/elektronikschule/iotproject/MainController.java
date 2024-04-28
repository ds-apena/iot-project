package de.elektronikschule.iotproject;

import de.elektronikschule.iotproject.persistence.Measurement;
import de.elektronikschule.iotproject.persistence.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/group2")
public class MainController {

   @Autowired
   private MeasurementRepository measurementRepository;

   @GetMapping(path = "/getMeasurements")
   public @ResponseBody Iterable<Measurement> getAllMeasurements() {
      return measurementRepository.findAll();
   }
}
