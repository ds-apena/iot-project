package de.elektronikschule.iotproject.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The Data access object class interacts with the repository interface
 * while exposing common methods to fetch data from the database.
 */
@Repository
public class MeasurementDAO {

   private MeasurementRepository repository;

   @Autowired
   public MeasurementDAO(MeasurementRepository repository) {
      this.repository = repository;
   }

   public List<Measurement> listAllMeasurements() {
      return repository.findAll();
   }

   public List<Measurement> listLastMeasurements() {
      return repository.findAll(PageRequest.of(0, 25, Sort.by(Sort.Direction.DESC,"timestamp"))).toList();
   }
}
