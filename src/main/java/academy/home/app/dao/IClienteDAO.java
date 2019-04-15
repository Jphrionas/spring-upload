package academy.home.app.dao;

import academy.home.app.domain.entity.Cliente;

public interface IClienteDAO {
	
	Iterable<Cliente> findAll();
	Cliente save(Cliente cliente);
	Cliente findById(Long id);
	Cliente findByName(String name);
	void delete(Long id);

}
