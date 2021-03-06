package cl.desquite.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.desquite.backend.entities.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

}
