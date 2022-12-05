package br.com.geofusion.cart;

import br.com.geofusion.cart.model.Client;
import br.com.geofusion.cart.model.Product;
import br.com.geofusion.cart.repository.ClientRepository;
import br.com.geofusion.cart.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class CartApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ProductRepository productRepository, ClientRepository clientRepository) {
		return args -> {
			productRepository.save(new Product(1000L,"Produto 1"));
			productRepository.save(new Product(2000L,"Produto 2"));
			productRepository.save(new Product(3000L,"Produto 3"));
			clientRepository.save(new Client("Teste Cliente 1","teste1@gmail.com","596.672.750-35"));
			clientRepository.save(new Client("Teste Cliente 2","teste2@gmail.com","897.190.750-90"));
			clientRepository.save(new Client("Teste Cliente 3","teste3@gmail.com","154.464.670-43"));
		};
	}

}
