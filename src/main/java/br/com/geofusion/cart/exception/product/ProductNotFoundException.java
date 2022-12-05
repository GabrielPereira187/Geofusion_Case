package br.com.geofusion.cart.exception.product;

//exceção para produto nao encontrado
public class ProductNotFoundException extends Exception{
    public ProductNotFoundException(Long cod){
        super("Produto de codigo "+ cod +" nao encontrado");
    }

}
