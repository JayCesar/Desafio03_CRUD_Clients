package com.devsuperior.dscrud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscrud.entities.Client;
import com.devsuperior.dscrud.entities.dto.ClientDTO;
import com.devsuperior.dscrud.repositories.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Client client = repository.findById(id).get();
		ClientDTO clientDTO = new ClientDTO(client);
		return clientDTO;
	}
	
	
	
	
}
