package br.com.geofusion.cart.service;

import br.com.geofusion.cart.exception.cart.CartNotFoundException;
import br.com.geofusion.cart.model.ShoppingCart;
import br.com.geofusion.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor

/*
    Serviço da classe de carrinho de compras
*/
public class CartService {

    private final CartRepository cartRepository;
    //Método que faz inserção um de carrinho de compra no banco
    public ShoppingCart createCart(ShoppingCart shoppingCart) {
        return cartRepository.save(shoppingCart);
    }
    //Método que faz busca um de carrinho de compra no banco
    public ShoppingCart getCart(Long clientId) throws CartNotFoundException {
        return cartRepository.findById(clientId).orElseThrow(() -> new CartNotFoundException(clientId));
    }
    //Método que faz busca de todos os carrinhos de compra no banco
    public Page<ShoppingCart> getCarts(Pageable pageable) {
        return cartRepository.findAll(pageable);
    }
    //Método que faz deleção de um carrinho de compra no banco
    public void deleteCart(Long clientId){
        cartRepository.deleteByClientID(clientId);
    }
}
