package br.com.geofusion.cart.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import one.util.streamex.StreamEx;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Classe que representa o carrinho de compras de um cliente.
 */

@Entity
@NoArgsConstructor
@Data
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long cart_id;

    @NotNull(message = "O codigo do cliente deve ser informado")
    private Long clientId;
    @OneToMany(mappedBy = "cart_id")
    private List<Item> items = new ArrayList<>();



    /**
     * Permite a adição de um novo item no carrinho de compras.
     *
     * Caso o item já exista no carrinho para este mesmo produto, as seguintes regras deverão ser seguidas:
     * - A quantidade do item deverá ser a soma da quantidade atual com a quantidade passada como parâmetro.
     * - Se o valor unitário informado for diferente do valor unitário atual do item, o novo valor unitário do item deverá ser
     * o passado como parâmetro.
     *
     * Devem ser lançadas subclasses de RuntimeException caso não seja possível adicionar o item ao carrinho de compras.
     *
     * @param product
     * @param unitPrice
     * @param quantity
     */
    public void addItem(Product product, BigDecimal unitPrice, int quantity) {
        Item item = new Item(product,unitPrice,quantity);
        if(productExistsInList(product)){
            Long index = StreamEx.of(items)
                            .indexOf(item1 -> item1.getProduct().getCode().equals(product.getCode())).getAsLong();
            items.set(Math.toIntExact(index), new Item(product,unitPrice, items.get(Math.toIntExact(index)).getQuantity() + quantity));
        }
        else{
            items.add(item);
        }
    }
    private boolean productExistsInList(Product product){
        return items
                .stream()
                .anyMatch(o -> o.getProduct().getCode()
                        .equals(product.getCode()));
    }

    /**
     * Permite a remoção do item que representa este produto do carrinho de compras.
     *
     * @param product
     * @return Retorna um boolean, tendo o valor true caso o produto exista no carrinho de compras e false
     * caso o produto não exista no carrinho.
     */
    public boolean removeItem(Product product) {
        return productExistsInList(product);
    }

    /**
     * Permite a remoção do item de acordo com a posição.
     * Essa posição deve ser determinada pela ordem de inclusão do produto na
     * coleção, em que zero representa o primeiro item.
     *
     * @param itemIndex
     * @return Retorna um boolean, tendo o valor true caso o produto exista no carrinho de compras e false
     * caso o produto não exista no carrinho.
     */
    public boolean removeItem(int itemIndex) {
        if(items.stream().count() - 1 < itemIndex)
            return false;
        return true;
    }

    /**
     * Retorna o valor total do carrinho de compras, que deve ser a soma dos valores totais
     * de todos os itens que compõem o carrinho.
     *
     * @return BigDecimal
     */
    public BigDecimal getAmount() {
        BigDecimal bd = BigDecimal.ZERO;
        if(this.items != null){
            for (Item item: this.items) {
                    bd = bd.add(item.getAmount());
            }
            return bd.setScale(2, RoundingMode.HALF_UP);
        }
        return bd;
    }

    /**
     * Retorna a lista de itens do carrinho de compras.
     *
     * @return items
     */
    public Collection<Item> getItems() {
        return this.items;
    }

    public ShoppingCart(Long clientId) {
        this.clientId = clientId;
    }




}