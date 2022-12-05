package br.com.geofusion.cart.exception.client;

//exceção para tentativa de inserção de cpf ja encontrado no banco
public class DuplicatedCpfException extends RuntimeException{
    public DuplicatedCpfException(String cpf) {
        super("Cliente com cpf "
                + cpf
                + " nao pode ser inserido devido a ja ter um cadastro");
    }

}
