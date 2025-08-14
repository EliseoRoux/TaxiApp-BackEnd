package com.centraltaxis.service;

// Importamos las clases necesarias para el servicio Cliente
import com.centraltaxis.model.Cliente;
// Importamos el repositorio ClienteRepository para realizar operaciones CRUD
import com.centraltaxis.repository.ClienteRepository;
// Importamos las clases necesarias para manejar listas
import java.util.List;
// Importamos las anotaciones necesarias para el servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Metodo para guardar un cliente
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Metodo para actualizar cliente con datos parciales
    

    // Metodo para borrar un cliente por ID
    public void eliminarClientePorId(int id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
        clienteRepository.delete(cliente);
    }

    // Metodo para buscar un cliente por ID
    public Cliente buscarClientePorId(int id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
    }

    // Metodo para listar todos los clientes
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

}
