package com.devsuperior.dscrud.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscrud.dto.ClientDTO;
import com.devsuperior.dscrud.entities.Client;
import com.devsuperior.dscrud.repositories.ClientRepository;
import com.devsuperior.dscrud.services.exeptions.DatabaseException;
import com.devsuperior.dscrud.services.exeptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Client client = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new ClientDTO(client);
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
		try {
			Client entity = repository.getReferenceById(id);
			copyToEntity(dto, entity);
			entity = repository.save(entity);
			return new ClientDTO(entity);
		}catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
	}
	
	// DELETE
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if(!repository.existsById(id)) throw new ResourceNotFoundException("Recurso não encontrado");
		try{
			repository.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
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
