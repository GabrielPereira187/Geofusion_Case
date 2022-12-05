package br.com.geofusion.cart.controller;


import br.com.geofusion.cart.exception.cart.CartNotFoundException;
import br.com.geofusion.cart.exception.client.ClientNotFoundException;
import br.com.geofusion.cart.exception.cart.InvalidIndexException;
import br.com.geofusion.cart.exception.product.ProductNotFoundException;
import br.com.geofusion.cart.factory.ShoppingCartFactory;
import br.com.geofusion.cart.model.Item;
import br.com.geofusion.cart.model.Product;
import br.com.geofusion.cart.model.ShoppingCart;
import br.com.geofusion.cart.service.CartService;
import br.com.geofusion.cart.service.ClientService;
import br.com.geofusion.cart.service.ItemService;
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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")

/*
    Controlador da classe de carrinho de compras
*/


public class CartController {

    //Inicialização dos construtores da classe

    private final CartService cartService;
    
    private final ProductService productService;

    private final ItemService itemService;

    private final ClientService clientService;

    private final ShoppingCartFactory shoppingCartFactory;

    //Método para criação de um carrinho de compras

    @PostMapping("/createCart/{clientId}")
    public ShoppingCart createCart(@PathVariable Long clientId) throws ClientNotFoundException {
        clientService.getClientById(clientId);
        ShoppingCart shoppingCart = shoppingCartFactory.create(clientId);
        return cartService.createCart(shoppingCart);
    }

    //Método para obtenção dos dados de um carrinho de compras
    @GetMapping("/getCart/{cartId}")
    public ShoppingCart getCart(@PathVariable Long cartId) throws CartNotFoundException {
        return cartService.getCart(cartId);
    }

    //Método que retorna todos os carrinhos de compras
    @GetMapping("/getCarts")
    public Page<ShoppingCart> getCarts(Pageable pageable) {
        return cartService.getCarts(pageable);
    }

    //Método que adiciona um item a um carrinho de compras, se o produto já existe no carrinho, há somente a atualização dos dados do produto
    @PostMapping("/addItemToCart/{cartID}")
    public ShoppingCart addItemToCart(@RequestBody @Valid Item item, @PathVariable Long cartID) throws CartNotFoundException, ProductNotFoundException {
        ShoppingCart shoppingCart = getCart(cartID);
        Product product = productService.getProductById(item.getProduct().getCode());
        item.setCart_id(cartID);
        for (Item i : shoppingCart.getItems()) {
            if(i.getProduct().getCode().equals(product.getCode())){
                shoppingCart.addItem(item.getProduct(),item.getUnitPrice(),item.getQuantity());
                itemService.updateItem(product.getCode(),cartID,item.getUnitPrice(),item.getQuantity());
                return shoppingCart;
            }
        }
        shoppingCart.addItem(item.getProduct(),item.getUnitPrice(),item.getQuantity());
        itemService.addItem(item);
        return shoppingCart;
    }

    //Método para deleção de um carrinho de compras
    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity<String> deleteCart(@PathVariable Long clientId){
        if(shoppingCartFactory.invalidate(clientId)) {
            cartService.deleteCart(clientId);
        }
        return ResponseEntity.ok("Carrinho deletado com sucesso");
    }

    //Método para deleção de um carrinho de compras via indice do item no carrinho
    @DeleteMapping("/deleteByIndex/{cartId}")
    public ResponseEntity<String> deleteCartItemByIndex(@PathVariable Long cartId, @RequestParam int index) throws Exception {
        ShoppingCart shoppingCart = getCart(cartId);
        if(shoppingCart.removeItem(index)){
            List<Item> listItem = shoppingCart.getItems().stream().toList();
            Item item = listItem.get(index);
            itemService.deleteByIndex(item.getItemID());
            return ResponseEntity.ok("Deletado com sucesso");
        }
        else{
            throw new InvalidIndexException(index);
        }
    }

    //Método para deleção de um carrinho de compras via codigo do produto
    @DeleteMapping("/deleteByProduct/{cartId}")
    public ResponseEntity<String> deleteCartItemByProductId(@PathVariable Long cartId, @RequestParam Long product_code) throws CartNotFoundException, ProductNotFoundException {
        ShoppingCart shoppingCart = getCart(cartId);
        Product product = productService.getProductById(product_code);
        if(shoppingCart.removeItem(product)){
            itemService.deleteByProduct(product);
            return ResponseEntity.ok("Produto de id "+ product_code + " encontrado na lista e deletado");
        }
        else{
            throw new ProductNotFoundException(product_code);
        }
    }

    //Método que retorna a media do valor total do carrinho de compras
    @GetMapping("/getAverage")
    public BigDecimal getAmount(Pageable pageable){
        shoppingCartFactory.getShoppingCarts(cartService.getCarts(pageable).stream().toList());
        return shoppingCartFactory.getAverageTicketAmount();
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
