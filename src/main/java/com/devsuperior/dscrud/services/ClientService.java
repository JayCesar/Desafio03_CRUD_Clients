package com.devsuperior.dscrud.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAll(Pageable pageable){
		Page<Client> result = repository.findAll(pageable);
		return result.map(x -> new ClientDTO(x));
	}
	
	// POST
	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		copyToEntity(dto, entity);
		entity = repository.save(entity);
		return new ClientDTO(entity);
	} 
	
	
	// PUT
	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		Client entity = repository.getReferenceById(id);
		copyToEntity(dto, entity);
		entity = repository.save(entity);
		return new ClientDTO(entity);
	}
	
	// DELETE
	@Transactional
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	
	private void copyToEntity(ClientDTO dto, Client entity) {
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		String date = dto.getBirthDate().toString();
		entity.setBirthDate(LocalDate.parse(date));
		entity.setChildren(dto.getChildren());	
	}
	
	

	
}
