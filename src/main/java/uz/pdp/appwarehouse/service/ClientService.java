package uz.pdp.appwarehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appwarehouse.entity.Client;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public Result addClient(Client client){
        boolean phoneNumber = clientRepository.existsByPhoneNumber(client.getPhoneNumber());
        if (phoneNumber){
            return new Result("Bunday raqam mavjud", false);
        }
        clientRepository.save(client);
        return new Result("Muvaffaqiyatli qoshildi", true);
    }

    public List<Client> getClientList(){
        List<Client> clientList = clientRepository.findAll();
        return clientList;
    }

    public Client getClient(Integer clientId){
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        return optionalClient.orElse(null);
    }

    public Result editClient(Integer clientId, Client client){
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isPresent()){
            Client client1 = optionalClient.get();
            client1.setName(client.getName());
            client1.setPhoneNumber(client.getPhoneNumber());
            clientRepository.save(client1);
            return new Result("muvaffaqiyatli ozgartirildi", true);
        }
        return new Result("Bunday client mavjud emas", false);
    }

    public Result deleteClient(Integer clientId){
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isPresent()){
            clientRepository.deleteById(clientId);
            return new Result("muvaffaqiyatli ochirildi", true);
        }
        return new Result("Bunday client mavjud emas", false);
    }
}
