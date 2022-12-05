package br.com.geofusion.cart.controller.advices;


import br.com.geofusion.cart.exception.cart.CartNotFoundException;
import br.com.geofusion.cart.exception.cart.InvalidIndexException;
import br.com.geofusion.cart.exception.client.ClientNotFoundException;
import br.com.geofusion.cart.exception.client.DuplicatedCpfException;
import br.com.geofusion.cart.exception.product.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    @ResponseBody
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String productNotFoundHandler(ProductNotFoundException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(CartNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String cartNotFoundHandler(CartNotFoundException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InvalidIndexException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String indexNotFoundHandler(InvalidIndexException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ClientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String ClientNotFoundHandler(ClientNotFoundException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(DuplicatedCpfException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String DataIntegrityViolationException(DuplicatedCpfException ex){
        return ex.getMessage();
    }



}
