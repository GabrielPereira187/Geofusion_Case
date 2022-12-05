package br.com.geofusion.cart.service;

import br.com.geofusion.cart.exception.product.ProductNotFoundException;
import br.com.geofusion.cart.model.Product;
import br.com.geofusion.cart.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor

/*
    Serviço da classe de produto
*/
public class ProductService {

    private final ProductRepository productRepository;

    //Método que faz atualização de produto no banco
    public Product replaceProduct(Product product) {
        return productRepository.findById(product.getCode())
                .orElseGet(() ->{
                    product.setCode(product.getCode());
                    return productRepository.save(product);
                });
    }
    //Método que faz busca um de produto no banco
    public Product getProductById(Long id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }
    //Método que faz busca de todos os produtos no banco
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    //Método que faz deleção um de produto no banco
    public  void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    //Método que faz inserção um de produto no banco
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}
