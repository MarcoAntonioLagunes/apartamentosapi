package com.demo.apartamentosapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.apartamentosapi.repositories.IClienteRepository;
import com.demo.apartamentosapi.repositories.IPropiedadRepository;
import com.demo.apartamentosapi.repositories.IReservaRepository;
import com.demo.apartamentosapi.models.ClienteModel;
import com.demo.apartamentosapi.models.PropiedadModel;
import com.demo.apartamentosapi.models.ReservaModel;
import com.demo.apartamentosapi.exceptions.ResourceNotFoundException;
import com.demo.apartamentosapi.exceptions.ReferentialIntegrityException;
import com.demo.apartamentosapi.exceptions.ResourceAlreadyExistsException;

@Service
public class ClienteService {
    @Autowired
    private IClienteRepository clienteRepository;
    @Autowired
    private IPropiedadRepository propiedadRepository;
    @Autowired
    private IReservaRepository reservaRepository;

    public List<ClienteModel> getAllClientes() {
        return clienteRepository.findAll();
    }

    public Optional<ClienteModel> getClienteById(Long id) {
        return clienteRepository.findById(id);
    }

    public ClienteModel saveCliente(ClienteModel cliente) {
        if (cliente.getEmail() != null) {
            Optional<ClienteModel> existente = clienteRepository.findByEmail(cliente.getEmail());
            if (existente.isPresent()) {
                throw new ResourceAlreadyExistsException("Cliente", "email", cliente.getEmail());
            }
        }
        if (cliente.getIne() != null) {
            Optional<ClienteModel> existente = clienteRepository.findByIne(cliente.getIne());
            if (existente.isPresent()) {
                throw new ResourceAlreadyExistsException("Cliente", "INE", cliente.getIne());
            }
        }
        return clienteRepository.save(cliente);
    }

    public void deleteCliente(Long id) {
        ClienteModel cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", "id", id));
        List<PropiedadModel> propiedades = propiedadRepository.findByPropietarioId(id);
        if (!propiedades.isEmpty()) {
            throw new ReferentialIntegrityException(
                    "No se puede eliminar el cliente porque tiene " + propiedades.size() +
                            " propiedad(es) asociada(s). Primero elimine o reasigne las propiedades.");
        }
        List<ReservaModel> reservas = reservaRepository.findByClienteId(id);
        long reservasActivas = reservas.stream()
                .filter(r -> r.getEstado() == ReservaModel.EstadoReserva.CONFIRMADA ||
                        r.getEstado() == ReservaModel.EstadoReserva.PENDIENTE)
                .count();
        if (reservasActivas > 0) {
            throw new ReferentialIntegrityException(
                    "No se puede eliminar el cliente porque tiene " + reservasActivas +
                            " reserva(s) activa(s) o pendiente(s). Primero cancele las reservas.");
        }
        clienteRepository.deleteById(id);
    }

    public Optional<ClienteModel> updateCliente(Long id, ClienteModel clienteDetails) {
        return clienteRepository.findById(id)
                .map(clienteToUpdate -> {
                    if (clienteDetails.getEmail() != null &&
                            !clienteDetails.getEmail().equals(clienteToUpdate.getEmail())) {
                        Optional<ClienteModel> existente = clienteRepository.findByEmail(clienteDetails.getEmail());
                        if (existente.isPresent()) {
                            throw new ResourceAlreadyExistsException("Cliente", "email", clienteDetails.getEmail());
                        }
                    }
                    if (clienteDetails.getIne() != null &&
                            !clienteDetails.getIne().equals(clienteToUpdate.getIne())) {
                        Optional<ClienteModel> existente = clienteRepository.findByIne(clienteDetails.getIne());
                        if (existente.isPresent()) {
                            throw new ResourceAlreadyExistsException("Cliente", "INE", clienteDetails.getIne());
                        }
                    }
                    clienteToUpdate.setTipo(clienteDetails.getTipo());
                    clienteToUpdate.setNombres(clienteDetails.getNombres());
                    clienteToUpdate.setApellidos(clienteDetails.getApellidos());
                    clienteToUpdate.setEmail(clienteDetails.getEmail());
                    clienteToUpdate.setTelefono(clienteDetails.getTelefono());
                    clienteToUpdate.setFechaNacimiento(clienteDetails.getFechaNacimiento());
                    clienteToUpdate.setIne(clienteDetails.getIne());
                    clienteToUpdate.setDireccion(clienteDetails.getDireccion());
                    clienteToUpdate.setEstatus(clienteDetails.getEstatus());
                    return clienteRepository.save(clienteToUpdate);
                });
    }
}
