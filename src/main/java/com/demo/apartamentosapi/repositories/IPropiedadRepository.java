package com.demo.apartamentosapi.repositories;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.demo.apartamentosapi.models.PropiedadModel;
@Repository
public interface IPropiedadRepository extends JpaRepository<PropiedadModel, Long> {
    List<PropiedadModel> findByTipo(PropiedadModel.TipoPropiedad tipo);
    List<PropiedadModel> findByPropietarioId(Long propietarioId);
}
