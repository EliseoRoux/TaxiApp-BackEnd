package com.centraltaxis.service;

import com.centraltaxis.dto.cliente.ClienteCreateDTO;
import com.centraltaxis.dto.cliente.ClienteResponseDTO;
import com.centraltaxis.dto.cliente.ClienteUpdateDTO;
import com.centraltaxis.mapper.ClienteMapper;
import com.centraltaxis.model.Cliente;
import com.centraltaxis.repository.ClienteRepository;
import com.centraltaxis.repository.ReservaRepository;
import com.centraltaxis.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    // Crear cliente desde DTO
    public ClienteResponseDTO crearCliente(ClienteCreateDTO clienteCreateDTO) {
        Cliente cliente = clienteMapper.toEntity(clienteCreateDTO);
        Cliente clienteGuardado = clienteRepository.save(cliente);
        return clienteMapper.toResponseDTO(clienteGuardado);
    }

    // Actualizar cliente completo desde DTO
    public ClienteResponseDTO actualizarCliente(int id, ClienteUpdateDTO clienteUpdateDTO) {
        Cliente clienteExistente = buscarClienteEntidadPorId(id);
        Cliente clienteActualizado = clienteMapper.toEntity(clienteUpdateDTO, clienteExistente);
        Cliente clienteGuardado = clienteRepository.save(clienteActualizado);
        return clienteMapper.toResponseDTO(clienteGuardado);
    }

    // Eliminar cliente por ID
    public void eliminarCliente(int id) {
        Cliente cliente = buscarClienteEntidadPorId(id);
        clienteRepository.delete(cliente);
    }

    // Obtener cliente como DTO
    public ClienteResponseDTO obtenerClientePorId(int id) {
        Cliente cliente = buscarClienteEntidadPorId(id);
        return clienteMapper.toResponseDTO(cliente);
    }

    // Listar todos los clientes como DTOs
    public List<ClienteResponseDTO> listarTodosLosClientes() {
        List<Cliente> clientes = clienteRepository.findAll();

        return clientes.stream()
                .map(cliente -> {
                    // Contar servicios y reservas
                    Integer serviciosCount = servicioRepository.countByCliente_IdCliente(cliente.getIdCliente());
                    Integer reservasCount = reservaRepository.countByCliente_IdCliente(cliente.getIdCliente());

                    // Mapear a DTO
                    ClienteResponseDTO dto = clienteMapper.toResponseDTO(cliente);

                    // Asignar los conteos
                    dto.setServiciosCount(serviciosCount);
                    dto.setReservasCount(reservasCount);
                    dto.setTotalServicios(serviciosCount + reservasCount);

                    return dto;
                })
                .collect(Collectors.toList());
    }

    // Método interno para buscar entidad (no expuesto)
    private Cliente buscarClienteEntidadPorId(int id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
    }

    // Método adicional útil: buscar por teléfono
    public ClienteResponseDTO buscarClientePorTelefono(String telefono) {
        Cliente cliente = clienteRepository.findByTelefono(telefono);
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado con teléfono: " + telefono);
        }
        return clienteMapper.toResponseDTO(cliente);
    }

    // Método adicional: verificar existencia
    public boolean existeCliente(int id) {
        return clienteRepository.existsById(id);
    }
}