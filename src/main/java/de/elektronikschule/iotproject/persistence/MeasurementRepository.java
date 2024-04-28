package de.elektronikschule.iotproject.persistence;

import de.elektronikschule.iotproject.persistence.Measurement;
import org.springframework.data.repository.CrudRepository;

public interface MeasurementRepository extends CrudRepository<Measurement, Integer> {
}
