package academy.home.app.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import academy.home.app.domain.entity.Cliente;

@Repository(value="clienteDAOImpl")
public class ClienteDAOImpl implements IClienteDAO {

	@PersistenceContext
	private EntityManager manager;
	
	
	@Override
	@Transactional(readOnly=true)
	public Iterable<Cliente> findAll() {
		return manager.createQuery("from Cliente", Cliente.class).getResultList();
	}


	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		Cliente find = findByName(cliente.getNome());
		if(find != null && cliente.getId() == null) {
			return find;
		}
		
		return manager.merge(cliente);
	}


	@Override
	@Transactional(readOnly=true)
	public Cliente findById(Long id) {
		return  Optional.ofNullable(manager.createQuery("select c from Cliente c where c.id = :id", Cliente.class)
			.setParameter("id", id)
			.getResultList().get(0)).orElse(null);
	}


	@Override
	@Transactional(readOnly=true)
	public Cliente findByName(String name) {
		List<Cliente> list = manager.createQuery("select c from Cliente c where c.nome = :nome", Cliente.class)
		.setParameter("nome", name)
		.getResultList();
		
		return list.isEmpty() ? null : list.get(0);
	}


	@Override
	@Transactional
	public void delete(Long id) {
		Cliente entity = findById(id);
		if(entity != null) {
			manager.remove(entity);
		}
		
	}

}
