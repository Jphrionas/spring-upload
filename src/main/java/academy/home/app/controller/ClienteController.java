package academy.home.app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import academy.home.app.domain.entity.Cliente;
import academy.home.app.domain.model.Paginator;
import academy.home.app.service.ClienteService;
import academy.home.app.service.UploadService;

@Controller
@RequestMapping("/cliente")
@SessionAttributes(names= {"cliente"}) // Guardando um objeto cliente na sessão
public class ClienteController {
	
	//@Qualifier("clienteDAOImpl") // Resolver Ambiguidades, selecionar um Bean concreto do Spring
	@Autowired
	private ClienteService clienteService;
	

	@Autowired
	private UploadService uploadService;
	
	@RequestMapping(path="/uploads/{fileName:.+}",  method=RequestMethod.GET)
	public ResponseEntity<Resource> downloadImage(@PathVariable("fileName") String fileName) {
		Resource urlResource = uploadService.getResourceFrom(fileName);
		return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + urlResource.getFilename() + "\"")
					.body(urlResource);
	}
	
	@RequestMapping(path="{id}",  method=RequestMethod.GET)
	public String findById(@PathVariable("id") Long id, Map<String, Object> model, RedirectAttributes flashScope, SessionStatus status) {
		
		Cliente cliente = clienteService.findById(id);
		if(cliente == null) {
			flashScope.addFlashAttribute("errorMessage", "Cliente is null");
			return "redirect:/cliente";
		}
		
		
		model.put("cliente", cliente);
		model.put("titulo", "Detalhe do Cliente");
		status.setComplete();
		return "view";
	}
	
	@RequestMapping(method= RequestMethod.GET)
	public String listar(
				@RequestParam(name="page", defaultValue="0") int page , 
				@RequestParam(name="size", defaultValue="5")  int size, 
				Model model) {
		
		// Busca mo Banco
		Pageable pageable = PageRequest.of(page, size);
		Page<Cliente> pageCliente = clienteService.findAll(pageable);
		Paginator<Cliente> paginator = new Paginator<>("/cliente", pageCliente);
		
		// Retorna para a View Já Paginado
		model.addAttribute("titulo", "Lista de Clientes");
		model.addAttribute("clientes", pageCliente);
		model.addAttribute("paginator", paginator);
		return "listar";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String create(@Valid Cliente cliente, BindingResult bindingResult,
				@RequestParam("file") MultipartFile file ,RedirectAttributes redirect, SessionStatus sessionStatus ) {
		
		if(bindingResult.hasErrors()) {
			return form(new  HashMap<>(), cliente);
		}		
		
		// Upload da Imagem
		if(!file.isEmpty()) {
			if(cliente.getId() != null) 
				uploadService.deleteImage(cliente.getFoto());

			String imageId = uploadService.uploadImage(file);
			cliente.setFoto(imageId);
		}
		
		clienteService.save(cliente);
		redirect.addFlashAttribute("successMessage", String.format("Cliente %s foi salvo com sucesso!", cliente.getNome()));
		redirect.addFlashAttribute("infoMessage", "Imagem Enviada com sucesso!");
		sessionStatus.setComplete(); // Finaliza o attributo, cliente da sessao
		return "redirect:/cliente";
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public String editar(@PathVariable("id") Long id, Map<String, Object> model, SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		Cliente cliente = this.clienteService.findById(id);
		return cliente != null ? form(model, cliente) : "redirect:/cliente/new";
	} 
	
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String form(Map<String, Object> model, Cliente cliente) {
		model.put("titulo", "Formulário do Cliente!");
		model.put("cliente", cliente);
		return "form";
	}
	
	@RequestMapping(value="/delete/{id}")
	@Transactional
	public String delete(@PathVariable("id") Long id, Map<String, Object> model, RedirectAttributes flashScoped,
			 SessionStatus sessionStatus) {
		Cliente cliente = this.clienteService.findById(id);
		if(cliente != null) {
			this.uploadService.deleteImage(cliente.getFoto());
		}
		
		this.clienteService.delete(cliente);
		flashScoped.addFlashAttribute("infoMessage", String.format("O cliente foi removido com sucesso!"));
		sessionStatus.isComplete();
		return "redirect:/cliente";
	}

}
