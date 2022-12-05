package br.com.geofusion.cart.repository;

import br.com.geofusion.cart.model.Client;
import org.springframework.data.repository.PagingAndSortingRepository;

//‘interface’ responsavel pelo metodo de manipulação dos dados no banco
public interface ClientRepository extends PagingAndSortingRepository<Client,Long> {
}
