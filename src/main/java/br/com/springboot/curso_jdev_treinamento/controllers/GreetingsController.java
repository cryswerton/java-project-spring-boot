package br.com.springboot.curso_jdev_treinamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev_treinamento.model.Usuario;
import br.com.springboot.curso_jdev_treinamento.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	@Autowired /* IC/CD/CDI - Injeção de Dependência */
	private UsuarioRepository usuarioRepository;
	
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "mostrarnome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Nome: " + name;
    }
    
    @RequestMapping(value = "/cadastrar/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String dizerOla(@PathVariable String nome) {
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	usuarioRepository.save(usuario); // Grava no banco de dados
    	
    	return "Usuário cadastrado: " + nome;
    }
    
    @GetMapping(value = "listartodos") // primeiro método de API (buscar todos)
    @ResponseBody // Retorna os dados para o corpo da resposta
    public ResponseEntity<List<Usuario>> listaUsuario(){
    	
    	// Executa a consulta no banco de dados 
    	List<Usuario> usuarios = usuarioRepository.findAll();
    	
    	// Retorna a lista em JSON
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
    	
    }
    
    
    @PostMapping(value = "salvar") // Mapeia a URL
    @ResponseBody // Descrição da resposta
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){
    	
    	Usuario user = usuarioRepository.save(usuario);
    	return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    }
    
    
    @PutMapping(value = "update") // Mapeia a URL
    @ResponseBody // Descrição da resposta
    public ResponseEntity<?> update(@RequestBody Usuario usuario){
    	
    	if(usuario.getId() == null) {
    		return new ResponseEntity<String>("Erro: id não informado.", HttpStatus.OK);
    	}
    	
    	Usuario user = usuarioRepository.saveAndFlush(usuario);
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }
    
    
    @DeleteMapping(value = "delete") // Mapeia a URL
    @ResponseBody // Descrição da resposta
    public ResponseEntity<String> delete(@RequestParam(name = "id") Long id){
    	
    	
    	usuarioRepository.deleteById(id);
    	
    	return new ResponseEntity<String>("Usuário deletado com sucesso.", HttpStatus.OK);
    }
    
    
    @GetMapping(value = "buscarid") // Mapeia a URL
    @ResponseBody // Descrição da resposta
    public ResponseEntity<Usuario> buscarPorID(@RequestParam(name = "id") Long id){
    	
    	
    	Usuario usuario = usuarioRepository.findById(id).get();
    	
    	return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }
    
    @GetMapping(value = "buscarnome") // Mapeia a URL
    @ResponseBody // Descrição da resposta
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name){
    	
    	
    	List<Usuario> usuarios = usuarioRepository.buscarPorNome(name.trim().toUpperCase());
    	
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); 
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
