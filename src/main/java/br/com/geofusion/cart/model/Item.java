package br.com.geofusion.cart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Classe que representa um item no carrinho de compras.
 */

@Entity
@NoArgsConstructor
@Data
@Table(name = "item")
public class Item implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemID;
    @ManyToOne
    @JoinColumn(name = "product_code")
    @Valid
    private Product product;

    @DecimalMin(value = "0.01", inclusive = false,message = "Valor unitario nao pode ser negativo ou igual a zero")
    @Digits(integer=3, fraction=2)
    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Min(value = 1,message = "Quantidade do produto nao pode ser negativa ou igual a zero")
    @Column(name = "quantity")
    private int quantity;

    @JsonIgnore
    @Column(name = "cart_id")
    private Long cart_id;

    /**
     * Construtor da classe Item.
     *
     * @param product
     * @param unitPrice
     * @param quantity
     */
    public Item(Product product, BigDecimal unitPrice, int quantity) {
        this.product = product;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    /**
     * Retorna o produto.
     *
     * @return Produto
     */
    public Product getProduct() {
        return this.product;
    }

    /**
     * Retorna o valor unitário do item.
     *
     * @return BigDecimal
     */
    public BigDecimal getUnitPrice() {
        return this.unitPrice;
    }

    /**
     * Retorna a quantidade dos item.
     *
     * @return int
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Retorna o valor total do item.
     *
     * @return BigDecimal
     */
    public BigDecimal getAmount() {
        return unitPrice.multiply(BigDecimal.valueOf(this.quantity)).setScale(2, RoundingMode.HALF_UP);
    }
}

