package br.com.geofusion.cart.exception.cart;


//exceção para carrinho nao encontrado
public class CartNotFoundException extends Exception{
    public CartNotFoundException(Long id){
        super("Carrinho de id "+ id + " nao encontrado");
    }

}
