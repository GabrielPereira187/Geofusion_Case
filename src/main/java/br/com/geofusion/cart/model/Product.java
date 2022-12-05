package br.com.geofusion.cart.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * Classe que representa um produto que pode ser adicionado
 * como item ao carrinho de compras.
 *
 * Importante: Dois produtos são considerados iguais quando ambos possuem o
 * mesmo código.
 */
@Setter
@Entity
@NoArgsConstructor
@Table(name = "product")
public class Product implements Serializable {

    @Id
    @Column(name = "product_code",unique = true)
    @NotNull(message = "Codigo do produto tem de ser informado")
    private Long code;

    @NotBlank(message = "Descrição do produto tem de ser informada")
    @Size(min = 5,max = 70,message = "Descrição do produto deve conter de 5 a 70 caracteres")
    @Column(name = "product_description")
    private String description;

    /**
     * Construtor da classe Produto.
     *
     * @param code
     * @param description
     */
    public Product(Long code, String description) {
        this.code = code;
        this.description = description;
    }


    /**
     * Retorna o código da produto.
     *
     * @return Long
     */
    public Long getCode() {
        return this.code;
    }

    /**
     * Retorna a descrição do produto.
     *
     * @return String
     */
    public String getDescription() {
        return this.description;
    }

}