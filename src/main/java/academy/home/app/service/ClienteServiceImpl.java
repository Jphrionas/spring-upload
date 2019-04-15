package academy.home.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import academy.home.app.domain.entity.Cliente;
import academy.home.app.repository.ClienteRepository;

@Service(value="impl1")
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	@Transactional(readOnly=true)
	public Iterable<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly=true)
	public  Page<Cliente> findAll(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		Cliente find = findByName(cliente.getNome());
		if(find != null && cliente.getId() == null) {
			return find;
		}
		
		return this.clienteRepository.save(cliente);
	}

	@Override
	@Transactional(readOnly=true)
	public Cliente findById(Long id) {
		return clienteRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public Cliente findByName(String name) {
		return clienteRepository.findByNome(name).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		Cliente cliente = findById(id);
		if(cliente != null) {
			this.clienteRepository.delete(cliente);
		}
	}

}
