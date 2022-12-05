package br.com.geofusion.cart.repository;

import br.com.geofusion.cart.model.Item;
import br.com.geofusion.cart.model.ShoppingCart;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.List;

//‘interface’ responsavel pelo metodo de manipulação dos dados no banco

public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {

    //query responsavel por deletar item via id
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from item where item_id = ?1")
    void deleteByItemID(Long itemId);

    //query responsavel por deletar item via codigo do produto
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from item where product_code = ?1")
    void deleteByProductID(Long product_code);

    //query responsavel por atualizar os dados de um produto em um ‘item’
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update item set unit_Price = ?3,quantity = quantity + ?4 where cart_id = ?2 and product_code = ?1")
    void updateProductItem(Long code, Long cartID, BigDecimal unitPrice, int quantity);
}
