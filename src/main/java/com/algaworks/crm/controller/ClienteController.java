package com.algaworks.crm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.crm.model.Cliente;
import com.algaworks.crm.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@GetMapping
	public List<Cliente> listarClientes(){
		return clienteRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Cliente> listarClientePorId(@PathVariable Long id) {
		return clienteRepository.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public List<Cliente> adicionar(@RequestBody List<Cliente> clientes) {
		List<Cliente> clientesSalvos = new ArrayList<Cliente>();
		for (Cliente cliente : clientes) {
			clientesSalvos.add(clienteRepository.save(cliente));
		}
		
		return clientesSalvos;
	}
	
	@DeleteMapping("/{id}")
	public String excluirCliente(@PathVariable Long id) {
		try {
			Cliente cliente = clienteRepository.getById(id);
			clienteRepository.delete(cliente);
			
			return "Cliente " + cliente.getNome() + " excluído com sucesso!";
		} catch (RuntimeException e) {
			return "Ocorreu um erro ao excluir o cliente";
		}
	}
}
