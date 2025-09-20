package com.centraltaxis.service;

import com.centraltaxis.dto.cliente.ClienteCreateDTO;
import com.centraltaxis.dto.cliente.ClienteResponseDTO;
import com.centraltaxis.dto.cliente.ClienteUpdateDTO;
import com.centraltaxis.mapper.ClienteMapper;
import com.centraltaxis.model.Cliente;
import com.centraltaxis.model.Reserva;
import com.centraltaxis.model.Servicio;
import com.centraltaxis.repository.ClienteRepository;
import com.centraltaxis.repository.ReservaRepository;
import com.centraltaxis.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// Importamos la clase Logger para registrar eventos
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime; // Asegúrate de que esta importación exista
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {
    // Instancia estática del Logger para esta clase
    private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    // --- LÓGICA DE CREACIÓN ---
    @Transactional
    public ClienteResponseDTO crearCliente(ClienteCreateDTO clienteCreateDTO) {
        log.info("Creando nuevo cliente: {}", clienteCreateDTO.getNombre());
        Cliente cliente = clienteMapper.toEntity(clienteCreateDTO);

        // Asignamos las fechas en el momento de la creación
        cliente.setFechaCreacion(LocalDateTime.now());
        cliente.setFechaActualizacion(LocalDateTime.now());

        Cliente clienteGuardado = clienteRepository.save(cliente);
        log.info("Cliente creado con ID: {}", clienteGuardado.getIdCliente());
        return clienteMapper.toResponseDTO(clienteGuardado);
    }

    // --- LÓGICA DE ACTUALIZACIÓN ---
    @Transactional
    public ClienteResponseDTO actualizarCliente(int id, ClienteUpdateDTO clienteUpdateDTO) {
        log.info("Actualizando cliente con ID: {}", id);
        log.debug("Datos recibidos para actualización: {}", clienteUpdateDTO.toString());
        Cliente clienteExistente = buscarClienteEntidadPorId(id);

        // El mapper aplica los cambios del DTO
        clienteMapper.updateEntityFromDTO(clienteUpdateDTO, clienteExistente);

        // Actualizamos la fecha de modificación
        clienteExistente.setFechaActualizacion(LocalDateTime.now());

        Cliente clienteGuardado = clienteRepository.save(clienteExistente);
        log.info("Cliente con ID: {} actualizado exitosamente.", id);
        return clienteMapper.toResponseDTO(clienteGuardado);
    }

    // Eliminar cliente por ID
    @Transactional
    public void eliminarCliente(int id) {
        log.info("Iniciando eliminación del cliente con ID: {}", id);

        // Buscamos si el cliente existe. Si no, lanza una excepción.
        if (!clienteRepository.existsById(id)) {
            // Usamos una excepción más específica que podemos manejar globalmente.
            throw new RuntimeException("Cliente no encontrado con ID: " + id);
        }

        // Buscamos y desvinculamos todos los servicios asociados.
        List<Servicio> serviciosAsociados = servicioRepository.findByCliente_IdCliente(id);
        if (!serviciosAsociados.isEmpty()) {
            log.warn("Desvinculando {} servicios del cliente ID: {}", serviciosAsociados.size(), id);
            for (Servicio servicio : serviciosAsociados) {
                servicio.setCliente(null);
            }
            servicioRepository.saveAll(serviciosAsociados);
        }

        // Hacemos lo mismo con las reservas.
        List<Reserva> reservasAsociadas = reservaRepository.findByCliente_IdCliente(id);
        if (!reservasAsociadas.isEmpty()) {
            log.warn("Desvinculando {} reservas del cliente ID: {}", reservasAsociadas.size(), id);
            for (Reserva reserva : reservasAsociadas) {
                reserva.setCliente(null);
            }
            reservaRepository.saveAll(reservasAsociadas);
        }

        // Ahora que no hay dependencias, podemos borrar el cliente de forma segura.
        clienteRepository.deleteById(id);
        log.info("Cliente con ID: {} eliminado correctamente.", id);
    }

    // Listar todos los clientes como DTOs
    public List<ClienteResponseDTO> listarTodosLosClientes() {
        List<Cliente> clientes = clienteRepository.findAll();

        return clientes.stream()
                .map(cliente -> {
                    Integer serviciosCount = servicioRepository.countByCliente_IdCliente(cliente.getIdCliente());
                    Integer reservasCount = reservaRepository.countByCliente_IdCliente(cliente.getIdCliente());
                    ClienteResponseDTO dto = clienteMapper.toResponseDTO(cliente);
                    dto.setServiciosCount(serviciosCount);
                    dto.setReservasCount(reservasCount);
                    dto.setTotalServicios(serviciosCount + reservasCount);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // --- MÉTODOS DE BÚSQUEDA (SIN CAMBIOS) ---
    public ClienteResponseDTO obtenerClientePorId(int id) {
        Cliente cliente = buscarClienteEntidadPorId(id);
        return clienteMapper.toResponseDTO(cliente);
    }

    private Cliente buscarClienteEntidadPorId(int id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
    }

    public ClienteResponseDTO buscarClientePorTelefono(String telefono) {
        Cliente cliente = clienteRepository.findByTelefono(telefono);
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado con teléfono: " + telefono);
        }
        return clienteMapper.toResponseDTO(cliente);
    }

    public boolean existeCliente(int id) {
        return clienteRepository.existsById(id);
    }
}