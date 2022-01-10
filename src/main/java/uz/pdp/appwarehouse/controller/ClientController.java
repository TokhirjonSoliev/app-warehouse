package uz.pdp.appwarehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appwarehouse.entity.Client;
import uz.pdp.appwarehouse.entity.Measurement;
import uz.pdp.appwarehouse.payload.Result;
import uz.pdp.appwarehouse.service.ClientService;

import java.util.List;

@RestController
@RequestMapping(value = "/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping
    public Result addClientController(@RequestBody Client client){
        return clientService.addClient(client);
    }

    // GET LIST, GET ONE, EDIT, DELETE
    @GetMapping
    public List<Client> getClientListController(){
        return clientService.getClientList();
    }

    @GetMapping(value = "/{clientId}")
    public Client getClientController(@PathVariable Integer clientId){
        return clientService.getClient(clientId);
    }

    @PutMapping(value = "/{clientId}")
    public Result editClientController(@PathVariable Integer clientId, @RequestBody Client client){
        return clientService.editClient(clientId, client);
    }

    @DeleteMapping(value = "/{clientId}")
    public Result deleteClientController(@PathVariable Integer clientId){
        return clientService.deleteClient(clientId);
    }
}
