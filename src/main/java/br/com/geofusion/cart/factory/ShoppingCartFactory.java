package br.com.geofusion.cart.factory;

import br.com.geofusion.cart.model.Item;
import br.com.geofusion.cart.model.ShoppingCart;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Classe responsável pela criação e recuperação dos carrinhos de compras.
 */
@Component
public class ShoppingCartFactory {

    private List<ShoppingCart> carts = new ArrayList<>();

    private List<ShoppingCart> cartArrayList = new ArrayList<>();




    /**
     * Cria e retorna um novo carrinho de compras para o cliente passado como parâmetro.
     *
     * Caso já exista um carrinho de compras para o cliente passado como parâmetro, este carrinho deverá ser retornado.
     *
     * @param clientId
     * @return ShoppingCart
     */
    public ShoppingCart create(Long clientId) {
        if(carts != null){
            Iterator<ShoppingCart> iterator = carts.iterator();
            while (iterator.hasNext()){
                ShoppingCart sc = iterator.next();
                if(sc.getClientId().equals(clientId)){
                    return sc;
                }
            }
        }
        ShoppingCart shoppingCart = new ShoppingCart(clientId);
        carts.add(shoppingCart);
        return shoppingCart;
    }

    /**
     * Retorna o valor do ticket médio no momento da chamada ao método.
     * O valor do ticket médio é a soma do valor total de todos os carrinhos de compra dividido
     * pela quantidade de carrinhos de compra.
     * O valor retornado deverá ser arredondado com duas casas decimais, seguindo a regra:
     * 0-4 deve ser arredondado para baixo e 5-9 deve ser arredondado para cima.
     *
     * @return BigDecimal
     */
    public BigDecimal getAverageTicketAmount() {
        BigDecimal bd = BigDecimal.ZERO;
        Iterator<ShoppingCart> iterator = cartArrayList.iterator();
        while (iterator.hasNext()){
            ShoppingCart sc = iterator.next();
            bd = bd.add(sc.getAmount());
        }
        return bd.divide(BigDecimal.valueOf(cartArrayList.size())).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Invalida um carrinho de compras quando o cliente faz um checkout ou sua sessão expirar.
     * Deve ser efetuada a remoção do carrinho do cliente passado como parâmetro da listagem de carrinhos de compras.
     *
     * @param clientId
     * @return Retorna um boolean, tendo o valor true caso o cliente passado como parämetro tenha um carrinho de compras e
     * e false caso o cliente não possua um carrinho.
     */
    public boolean invalidate(Long clientId) {
        if(carts != null){
            Iterator<ShoppingCart> iterator = carts.iterator();
            while (iterator.hasNext()){
                ShoppingCart sc = iterator.next();
                if(sc.getClientId().equals(clientId)){
                    carts.remove(sc);
                    return true;
                }
            }
        }
        return false;
    }


    public void getShoppingCarts(List<ShoppingCart> cartList){
        cartArrayList = cartList;
    }


}
