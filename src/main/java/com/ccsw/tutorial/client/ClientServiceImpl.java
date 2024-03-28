package com.ccsw.tutorial.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;

import jakarta.transaction.Transactional;

/**
 * @author ccsw
 *
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Client get(Long id) {

        return this.clientRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Client> findAll() {

        return (List<Client>) this.clientRepository.findAll();
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void save(Long id, ClientDto dto) throws Exception {

        if (existsByName(dto.getName())) {
            throw new Exception("Already exists");
        } else {
            Client client;

            if (id == null) {
                client = new Client();
            } else {
                client = this.get(id);
            }

            client.setName(dto.getName());

            this.clientRepository.save(client);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.get(id) == null) {
            throw new Exception("Not exists");
        }

        this.clientRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsByName(String name) {

        List<Client> existingClients = (List<Client>) clientRepository.findAll();
        boolean alreadyExists = false;

        for (Client existingClient : existingClients) {
            if (existingClient.getName().equals(name)) {
                alreadyExists = true;
            }
        }

        return alreadyExists;
    }
}
