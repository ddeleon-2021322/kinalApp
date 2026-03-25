package com.deleondiego.kinalapp.service;

import com.deleondiego.kinalapp.entity.Cliente;
import java.util.List;
import java.util.Optional;

public interface IClienteService {
    /*
    *Interfaz: Es un contrato que dice que metodos debe tener
    * cualquier servicio de clientes,
    * no tiene implementacion solo la definicion de los metods
    */

    //Metodo que devuelve una lista de todos los clientes
    List<Cliente> listarTodos();
    /*
    *List<Clientes> lo que hace es devolver una lista
    * de objetos de la entidad Clientes
    */

    List<Cliente> listarPorEstado(int estado);
    //List<Cliente>: Devuelve una lista
    //Integer estado: Recibira 0 o 1 para comparar si esta activo o inactivo


    //Metodo que guarda un cliente en la BD
    Cliente guardar(Cliente cliente);
    //Parametros: Parametros que recibe un objeto Cliente con los datos
    //guardar

    //optional - Contenedor que puede tener o no valor
    //Evita el error de NullPointerException
    Optional<Cliente> buscarPorDPI(String dpi);


    //Metodo que actualiza un Cliente
    Cliente actualizar(String dpi, Cliente cliente);
    /*
    *Parametros - dpi: DPI del cliente a actualizar
    * Cliente cliente: Objeto con los datos nuevos
    * Retornar un objeto de tipo cliente ya actializado
    */

    /*
    * Metodo de tipo void para eliminar a un cliente
    * void: no retorna ningun valor o ningun dato
    * Elimina un Cliente por su DPI
    */
    void eliminar(String dpi);

    //boolean - Retornar true si exite y false si no existe
    boolean existePorDPI (String dpi);
}
