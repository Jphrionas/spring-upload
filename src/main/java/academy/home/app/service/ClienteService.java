package academy.home.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import academy.home.app.domain.entity.Cliente;

public interface ClienteService {

	Iterable<Cliente> findAll();
	Page<Cliente> findAll(Pageable pageable);
	Cliente save(Cliente cliente);
	Cliente findById(Long id);
	Cliente findByName(String name);
	void delete(Long id);
}
