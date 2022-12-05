package br.com.geofusion.cart.exception.cart;

//exceção para ‘item’ nao existente no carrinho
public class InvalidIndexException extends Exception{
    public InvalidIndexException(int index){
        super("O indice: "+ index + " nao foi encontrado");
    }
}
