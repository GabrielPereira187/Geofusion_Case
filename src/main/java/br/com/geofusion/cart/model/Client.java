package br.com.geofusion.cart.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * Classe que representa um cliente.
 */


@Entity
@Table(name = "customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client implements Serializable{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientId;
    @Size(min = 4,max = 50, message = "Nome do cliente deve ter de 4 a 50 caracteres")
    @NotEmpty(message = "Nome do cliente deve ser informado")
    @Column(name = "client_name")
    private String name;
    @Email(message = "Insira um e-mail válido")
    @NotEmpty(message = "E-mail do cliente deve ser informado")
    @Column(name = "client_email")
    private String email;
    @CPF(message = "Insira um cpf válido")
    @NotEmpty(message = "Cpf do cliente deve ser informado")
    @Column(unique = true,name = "client_cpf")
    private String cpf;


    /**
     * Construtor da classe cliente.
     *
     * @param name
     * @param email
     * @param cpf
     */
    public Client(String name, String email, String cpf) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
    }
}
