package br.com.geofusion.cart.exception.client;

//exceção para cliente nao encontrado
public class ClientNotFoundException extends Exception {

    public ClientNotFoundException(Long id){
        super("Cliente de id "+ id + " nao encontrado");
    }
}
