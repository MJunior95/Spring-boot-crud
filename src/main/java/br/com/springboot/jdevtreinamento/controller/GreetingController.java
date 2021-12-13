package br.com.springboot.jdevtreinamento.controller;

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

import br.com.springboot.jdevtreinamento.model.Usuario;
import br.com.springboot.jdevtreinamento.repository.UsuarioRepository;

@RestController
public class GreetingController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
	public String retornaOlaMundo(@PathVariable String nome) {
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuarioRepository.save(usuario);
		return "Ola mundo " + nome;
	}

	@GetMapping(value = "/listatodos")
	@ResponseBody
	public ResponseEntity<List<Usuario>> listaUsuario() {

		List<Usuario> usuarios = usuarioRepository.findAll();

		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}

	@PostMapping(value = "/salvar")
	@ResponseBody
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
		Usuario user = usuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
	}
	@PutMapping(value = "/atualizar")
	@ResponseBody
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) {
		if(usuario.getId() == null) {
			
			return new ResponseEntity<String>("Id não foi informado para atualizacao", HttpStatus.OK);
		}
		Usuario user = usuarioRepository.saveAndFlush(usuario);
		
		
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}

	@DeleteMapping(value = "/deletar")
	@ResponseBody
	public ResponseEntity<String> deletar(@RequestParam Long iduser) {
		usuarioRepository.deleteById(iduser);

		return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);
	}
	
	@GetMapping(value = "/buscaruserid")
	@ResponseBody
	public ResponseEntity<Usuario> buscarUserId(@RequestParam(name = "iduser") Long iduser) {
		
		Usuario user = usuarioRepository.findById(iduser).get();
		
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}
	
	@GetMapping(value = "/buscarPorNome")
	@ResponseBody
	public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "nome") String nome) {
		
		List<Usuario> user = usuarioRepository.buscarPorNome(nome.trim().toUpperCase());
		
		return new ResponseEntity<List<Usuario>>(user, HttpStatus.OK);
	}
}
