package br.com.geofusion.cart.repository;

import br.com.geofusion.cart.model.ShoppingCart;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

//‘interface’ responsavel pelo metodo de manipulação dos dados no banco

public interface CartRepository extends PagingAndSortingRepository<ShoppingCart,Long> {

    //query responsavel por deletar carrinho via id do cliente
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from shopping_cart where client_id = ?1")
    void deleteByClientID(Long clientId);
}
