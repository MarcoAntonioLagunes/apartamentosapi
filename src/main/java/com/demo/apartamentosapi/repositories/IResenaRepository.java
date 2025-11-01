package com.demo.apartamentosapi.repositories;
import com.demo.apartamentosapi.models.ResenaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface IResenaRepository extends JpaRepository<ResenaModel, Integer> {
    List<ResenaModel> findByReservacionId(Integer reservacionId);
    @Query("SELECT r FROM ResenaModel r WHERE r.reservacion.propiedad.id_propiedad = :propiedadId")
    List<ResenaModel> findByPropiedadId(@Param("propiedadId") Long propiedadId);
    @Query("SELECT r FROM ResenaModel r WHERE r.reservacion.cliente.id_cliente = :clienteId")
    List<ResenaModel> findByClienteId(@Param("clienteId") Long clienteId);
    @Query("SELECT r FROM ResenaModel r WHERE r.respuestaPropietario IS NOT NULL")
    List<ResenaModel> findResenasWithRespuestaPropietario();
}
