package com.herbertgarcia.kinalapp.controller;

import com.herbertgarcia.kinalapp.entity.Cliente;
import com.herbertgarcia.kinalapp.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RestController = @Controller + @ResponseBody
@RequestMapping("/clientes")
//Todas las rutas en este controlador deben empezar con /clientes
public class ClienteController {

    private final ClienteService clienteService;


    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    //Responde a peticiones GET
    @GetMapping
    //ResponseEntity nos permite controlar el código HTTP y el cuerpo
    public ResponseEntity<List<Cliente>> listar() {
        List<Cliente> clientes = clienteService.listarTodos();
        //delegamos al servicio
        return ResponseEntity.ok(clientes);
        //200 OK con la lista de clientes
    }

    //{dpi} es una variable de ruta(valor a buscar)
    @GetMapping("/{dpi}")
    public ResponseEntity<Cliente> buscarPorDPI(@PathVariable String dpi) {
        //@PathVariable Toma el valor de la URL y lo asigna al dpi
        return clienteService.buscarPorDPI(dpi)
                //Si optional tiene valor, devuelve 200 ok con el cliente
                .map(ResponseEntity::ok)
                //Si optional esta vació, devuelve 404 NOT FOUND
                .orElse(ResponseEntity.notFound().build());
    }

    //POST crear un nuevo cliente
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Cliente cliente) {
        //@RequestBody: Toma el JSON del cuerpo y lo convierte a un objeto de tipo Cliente
        //<?> significa "tipo genérico" puede ser un Cliente o un String
        try {
            Cliente nuevoCliente = clienteService.guardar(cliente);
            //Intentamos guardar el cliente, pero puede lanzar una excepción
            //de IllegalArgumentException
            return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
            //201 CREATED(mucho más especifico que el 2200 para la creación de un cliente)
        } catch (IllegalArgumentException e) {
            //Sí hay error de validación
            return ResponseEntity.badRequest().body(e.getMessage());
            //400 BAD REQUEST con el mensaje de error
        }
    }

    //DELETE elimina un cliente
    @DeleteMapping("/{dpi}")
    public ResponseEntity<Void> eliminar(@PathVariable String dpi) {
        //ResponseEntity<Void>: No devuelve cuerpo en la respuesta
        try {
            if (!clienteService.existePorDPI(dpi)) {
                return ResponseEntity.notFound().build();
                //404 si no existe
            }
            clienteService.eliminar(dpi);
            return ResponseEntity.noContent().build();
            //204 NO CONTENT (se ejecutó correctamente y no devuelve cuerpo)
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
            //404 NOT FOUND
        }
    }

    //Listar usuarios activos
    @GetMapping("/activos")
    public ResponseEntity<List<Cliente>> listarActivos() {
        List<Cliente> clientes = clienteService.listarActivos();
        return ResponseEntity.ok(clientes);
    }

    //Actualizar cliente a través de DPI
    @PutMapping("/{dpi}")
    public ResponseEntity<?> actualizar(@PathVariable String dpi, @RequestBody Cliente cliente){
        try{
            if(!clienteService.existePorDPI(dpi)){
                //Verificar si existe antes de poder actualizar
                //404 NOT FOUND
                return ResponseEntity.notFound().build();
            }
            //Actualizar el cliente, pero esto puede lanzar una excepción
            Cliente clienteActualizado = clienteService.actualizar(dpi, cliente);
            return ResponseEntity.ok(clienteActualizado);
            //200 ok con el cliente ya actualizado
        }catch(IllegalArgumentException e){
            //Error cuando los datos son incorrectos
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (RuntimeException e){
            //Posiblemente, cualquier error como: cliente no encontrado, etc.
            //404 NOT FOUND
            return ResponseEntity.notFound().build();
        }
    }
}
