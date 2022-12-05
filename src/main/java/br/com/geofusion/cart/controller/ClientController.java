package br.com.geofusion.cart.controller;


import br.com.geofusion.cart.exception.client.ClientNotFoundException;
import br.com.geofusion.cart.model.Client;
import br.com.geofusion.cart.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client")

/*
    Controlador da classe de cliente
*/

public class ClientController {
    //Inicialização dos construtores da classe
    private final ClientService clientService;

    //Método para criação de um cliente
    @PostMapping("/save")
    public ResponseEntity<Client> saveClient(@RequestBody @Valid Client client){
        clientService.saveClient(client);
        return ResponseEntity.ok().body(client);
    }
    //Método para obtenção dos dados de um cliente
    @GetMapping("/getClient/{id}")
    public Client getClientById(@PathVariable Long id) throws ClientNotFoundException {
        return clientService.getClientById(id);
    }
    //Método que retorna todos os clientes
    @GetMapping("/getClients")
    public Page<Client> getAllClients(Pageable pageable) {
        return clientService.getAllClients(pageable);
    }

    //Método para deleção de um cliente
    @DeleteMapping("/delete/{id}")
    public void deleteClient(@PathVariable Long id) throws ClientNotFoundException {
        clientService.getClientById(id);
        clientService.deleteClient(id);
    }
    //Método para atualização de um cliente
    @PutMapping("/update/{id}")
    public Client replaceClient(@RequestBody @Valid Client client, @PathVariable Long id){
        return clientService.replaceClient(client,id);
    }
    //Metodo para lidar com exceções invalidas quando o status retornado for 400(bad request)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
