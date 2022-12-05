package br.com.geofusion.cart.controller;


import br.com.geofusion.cart.exception.product.ProductNotFoundException;
import br.com.geofusion.cart.model.Product;
import br.com.geofusion.cart.service.ProductService;
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
@RequestMapping("/api/v1/product")
/*
    Controlador da classe de produto
*/
public class ProductController {
    //Inicialização dos construtores da classe
    private final ProductService productService;

    //Método para criação de um produto
    @PostMapping("/save")
    public ResponseEntity<Product> saveProduct(@RequestBody @Valid Product product){
        Product productToBeSaved = new Product(product.getCode(),product.getDescription());
        productService.saveProduct(productToBeSaved);
        return ResponseEntity.ok().body(productToBeSaved);
    }
    //Método para obtenção dos dados de um produto
    @GetMapping("/getProduct/{id}")
    public Product getProductById(@PathVariable Long id) throws ProductNotFoundException {
        return productService.getProductById(id);
    }
    //Método que retorna todos os produtos
    @GetMapping("/getProducts")
    public Page<Product> getAllProducts(Pageable pageable) {
        return productService.getAllProducts(pageable);
    }
    //Método para deleção de um cliente
    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        productService.getProductById(id);
        productService.deleteProduct(id);
    }
    //Método para atualização de um cliente
    @PutMapping("/update")
    public Product replaceProduct(@RequestBody @Valid Product product){
        Product productToBeUpdated = new Product(product.getCode(),product.getDescription());
        return productService.replaceProduct(productToBeUpdated);
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
