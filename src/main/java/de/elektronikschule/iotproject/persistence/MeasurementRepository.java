package de.elektronikschule.iotproject.persistence;

import de.elektronikschule.iotproject.persistence.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
}
