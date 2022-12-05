package br.com.geofusion.cart.service;


import br.com.geofusion.cart.exception.client.ClientNotFoundException;
import br.com.geofusion.cart.exception.client.DuplicatedCpfException;
import br.com.geofusion.cart.model.Client;
import br.com.geofusion.cart.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
/*
    Serviço da classe de cliente
*/
public class ClientService {

    private final ClientRepository clientRepository;
    //Método que faz inserção de cliente no banco
    public Client saveClient(Client client) {
        try{
             client = clientRepository.save(client);
        }catch (DataIntegrityViolationException ex){
            throw new DuplicatedCpfException("Cliente com cpf "
                    + client.getCpf()
                    + " nao pode ser inserido devido a estar duplicado");
        }
        return client;
    }
    //Método que faz busca um de cliente no banco
    public Client getClientById(Long id) throws ClientNotFoundException {
        return clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
    }
    //Método que faz busca de todos os clientes no banco
    public Page<Client> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }
    //Método que faz deleção de produto no banco
    public void deleteClient(Long id) {
        clientRepository.findById(id);
        clientRepository.deleteById(id);
    }
    //Método que faz atualização de cliente no banco
    public Client replaceClient(Client clientToBeUpdated, Long id) {
        return clientRepository.findById(id)
                .map(client -> {
                    client.setName(clientToBeUpdated.getName());
                    client.setCpf(clientToBeUpdated.getCpf());
                    client.setEmail(clientToBeUpdated.getEmail());
                    try{
                        return clientRepository.save(client);
                    }catch (DataIntegrityViolationException ex){
                        throw new DuplicatedCpfException(clientToBeUpdated.getCpf());
                    }
                }).orElseGet(() ->{
                    clientToBeUpdated.setClientId(id);
                    try{
                        return clientRepository.save(clientToBeUpdated);
                    }catch (DataIntegrityViolationException ex){
                        throw new DuplicatedCpfException(clientToBeUpdated.getCpf());
                    }
                });
    }
}
