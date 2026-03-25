package com.deleondiego.kinalapp.service;

import com.deleondiego.kinalapp.entity.Cliente;
import com.deleondiego.kinalapp.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
/*
* Anotacion que registra un Bean como un Bean de spreing
* Que la clase contiene la logica del negocio
 */
@Service
/*
* Por defecto todos los metodos de esta clase seran transaccionales
* una transaccion es que puede o no ocurrir algo
 */
@Transactional

public class ClienteService implements IClienteService {
    /*
    * Private: Solo es accesible dentro de la misma clase
    * Final: No puede cambiar porque es contante
    * ClienteRepository: es el repositorio para acceder a la base de datos
    * Inyeccion de Dependencias ya que spring nos da el repositorio
     */
    private final ClienteRepository clienteRepository;

    /*
    * Constructor: este se ejecuta al crear un objeto
    * Spring pasa el repositorio automaticamente (inyeccion de dependencias)
     */

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
        //Asignar el repositorio a nuestra variable de clase
    }

    //Indica que esta implementando un metodo de la interfaz
    @Override
    //Optimizar la consulta, solo lectura, para que no bloquee la base de datos
    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
        //findall(): es un metodo de que hace el select * from Clientes
        //Este metodo es de JPARepository
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        /*
        *Metodo de guardar crea un cliente
        * aca es donde colocamos la logica del negocio antes de guardar
        * primero validamos el dato
         */
        validarCliente(cliente);
        return clienteRepository.save(cliente);
    }

    @Override
    public Optional<Cliente> buscarPorDPI(String dpi) {
        //Buscar un cliente por dpi
        return clienteRepository.findById(dpi);
        //Optional nos evita el null pointer exception
    }

    @Override
    @Transactional(readOnly = true)
    //readOnly solo trae datos y no modifica nada de la db
    public List<Cliente> listarPorEstado(int estado) {

        // Valiudamos que sea 1 y 0
        if (estado != 0 && estado != 1) {
            throw new IllegalArgumentException("El estado debe ser 0 (inactivo) o 1 (activo)");
        }

        return clienteRepository.findAll()
                .stream()
                .filter(cliente -> cliente.getEstado() == estado)
                //.filter filtra con la condicion dada
                //.toList lista coin la condicion
                .toList();
    }

    @Override
    public Cliente actualizar(String dpiViejo, Cliente clienteNuevo) {
        // 1. Verificar si el cliente existe realmente con el DPI de la URL
        if (!clienteRepository.existsById(dpiViejo)) {
            throw new RuntimeException("No existe un cliente con el DPI: " + dpiViejo);
        }

        validarCliente(clienteNuevo);

        // 2. Lógica para CAMBIAR el DPI
        // Si el DPI que viene en el JSON es distinto al de la URL:
        if (!dpiViejo.equals(clienteNuevo.getDPICliente())) {
            // Eliminamos el registro con el ID viejo
            clienteRepository.deleteById(dpiViejo);
            // El .save() siguiente creará el registro con el ID nuevo
        }

        return clienteRepository.save(clienteNuevo);
    }

    @Override
    public void eliminar(String dpi) {
        //Eliminar un cliente
        if (!clienteRepository.existsById(dpi)){
            throw new RuntimeException("El cliente no se encontro con el dpi" + dpi);
        }
        clienteRepository.deleteById(dpi);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorDPI(String dpi) {
        //verificamos si existe un cliente
        return clienteRepository.existsById(dpi);
    }

    //Metodo privado que solo puede usarce dentro de la clase
    private void validarCliente(Cliente cliente){
        /*
        * Validaciones del negocio: este metodo se hara privado por que es algo interno del servicio
         */
        if (cliente.getDPICliente() == null || cliente.getDPICliente().trim().isEmpty()){
            // Si el dpi es null o esta vacio despues de quitar espacios
            // Lanza una excepcion con un mensaje
            throw new IllegalArgumentException("El DPI es un dato obligatorio");
        }
        if (cliente.getNombreCliente() == null || cliente.getNombreCliente().trim().isEmpty()){
            throw new IllegalArgumentException("El nombre es un dato obligatorio");
        }
        if (cliente.getApellidoCliente() == null || cliente.getApellidoCliente().trim().isEmpty()){
            throw new IllegalArgumentException("El apellido es un dato obligatorio ");
        }

    }
}


