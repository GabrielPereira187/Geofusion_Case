package br.com.geofusion.cart.repository;


import br.com.geofusion.cart.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

//‘interface’ responsavel pelo metodo de manipulação dos dados no banco
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
