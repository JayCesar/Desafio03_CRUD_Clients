package com.devsuperior.dscrud.dto;

import java.time.LocalDate;

import com.devsuperior.dscrud.entities.Client;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ClientDTO {
	
	private Long id;
	
	@Size(min = 3, max = 100, message = "Nome precisa ter entre 3 a 100 caracteres")
	@NotBlank(message = "Campo requerido")
	private String name;
	
	@Size(max = 11, message = "CPF deve conter 11 caracteres")
	@NotBlank(message = "Campo requerido")
	private String cpf;
	
	@Positive(message = "O Salário deve ser positivo")
	private Double income;
	
	@JsonFormat(pattern = "yyyy/MM/dd")
	private LocalDate birthDate;
	
	@Positive(message = "O número de filhos deve ser positivo")
	private Integer children;
	
	public ClientDTO(Long id, String name, String cpf, Double income, LocalDate birthDate, Integer children) {
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.income = income;
		this.birthDate = birthDate;
		this.children = children;
	}
	
	public ClientDTO(Client entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.cpf = entity.getCpf();
		this.income = entity.getIncome();
		this.birthDate = entity.getBirthDate();
		this.children = entity.getChildren();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCpf() {
		return cpf;
	}

	public Double getIncome() {
		return income;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public Integer getChildren() {
		return children;
	}
	
	
	
	
}
