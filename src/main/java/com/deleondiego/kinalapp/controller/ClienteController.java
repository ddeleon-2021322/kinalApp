package com.deleondiego.kinalapp.controller;

import com.deleondiego.kinalapp.entity.Cliente;
import com.deleondiego.kinalapp.repository.ClienteRepository;
import com.deleondiego.kinalapp.service.ClienteService;
import com.deleondiego.kinalapp.service.IClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RestController = @Controller +  @RequestMapping
@RequestMapping("/clientes")
//Todas las rutas de este controlador deben empezar por /clientes
public class ClienteController {
    //Inyectamos el servicio y NO el repositorio
    //El controlador solo debe de tener conexion con el servicio

    private final IClienteService clienteService;
    // como buena practica la Inyeccion de dependencias debe hacerse por el constructor
    public ClienteController(IClienteService clienteService) {

        this.clienteService = clienteService;
    }
    @GetMapping
    //ResponseEntity nos permite
    public ResponseEntity <List<Cliente>> listar(){
        List<Cliente> clientes = clienteService.listarTodos();

        return ResponseEntity.ok(clientes);
    }
    //{dpi} solo es una variable de ruta (valor a buscar)
    @GetMapping("/{dpi}")
    public ResponseEntity<Cliente> buscarPorDPI(@PathVariable String dpi){
        //@PathVariable toma el valor de la URL y lo asigna al DPI
        return clienteService.buscarPorDPI(dpi)
                //Si optional tiene valor devuelve 200 ok con el cliente
                .map(ResponseEntity::ok)
                //Si optional esta vacio, evuelve 404 not found
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Cliente>> listarPorEstado(@PathVariable int estado) {
        try {
            List<Cliente> clientes = clienteService.listarPorEstado(estado);
            return ResponseEntity.ok(clientes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //POST crea un nuevo cliente
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Cliente cliente) {
        //@Request body toma el Json del cuerpo y lo convierte en un objeto de tipo cliente
        //<?> Significa que es un "tipo generico" que puede ser un cliente o un String
        try {
            Cliente nuevoCliente = clienteService.guardar(cliente);
            //Intentamos guardar el cliente pero puede lanzar una excepcion
            //de IllegalArgument
            return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            //Si hay un error de validacion
            //400 BAD REQUEST con el mendsaje de error
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }
    //Delete elimina un cliente
    @DeleteMapping("/{dpi}")
    public ResponseEntity<Void> eliminar(@PathVariable String dpi){
        //ResponseEntity<Void>: No devuelve cuerpo a la respuesta
        try{
            if (!clienteService.existePorDPI(dpi)){
                return ResponseEntity.notFound().build();
                //404 Si no existe
            }
            clienteService.eliminar(dpi);
            return ResponseEntity.ok().build();

        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
            //404 NOT FOUND

        }
    }
    //Actualizar cliente a traves del dpi
    @PutMapping("/{dpi}")
    public ResponseEntity<?> actualizar(@PathVariable String dpi, @RequestBody Cliente cliente){
        try{
            if(!clienteService.existePorDPI(dpi)){
                //Verificar si existe antes de poder actualizar
                return ResponseEntity.notFound().build();
                //404 NOT FOUND
            }
            //Actualizamos el cliente pero esto puede lanzar una excepcion
            Cliente clienteActualizado = clienteService.actualizar(dpi, cliente);
            return ResponseEntity.ok(clienteActualizado);
            //200 con el cliente ya actualizado

        }catch(IllegalArgumentException e){
            //Error cuando los datos sean incorrectos
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch(RuntimeException e){
            // Posiblemente otro error como: cliente no encontrado
            //404 not found
            return ResponseEntity.notFound().build();
        }
    }


}