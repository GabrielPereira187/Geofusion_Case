package br.com.geofusion.cart.service;


import br.com.geofusion.cart.model.Item;
import br.com.geofusion.cart.model.Product;
import br.com.geofusion.cart.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor

/*
    Serviço da classe de item
*/
public class ItemService {


    private final ItemRepository itemRepository;

    //Método que faz inserção de um ‘item’ no banco
    public Item addItem(Item item){
        return itemRepository.save(item);
    }
    //Método que faz deleção de um ‘item’ via index no banco
    public void deleteByIndex(Long itemID) {
        itemRepository.deleteByItemID(itemID);
    }
    //Método que faz deleção de um ‘item’ via codigo de produto no banco
    public void deleteByProduct(Product product) {
        itemRepository.deleteByProductID(product.getCode());
    }
    //Método que faz atualização de um ‘item’ via codigo de produto no banco
    public void updateItem(Long code, Long cartID, BigDecimal unitPrice, int quantity) {
         itemRepository.updateProductItem(code,cartID,unitPrice,quantity);
    }
}
