package cl.desquite.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.desquite.backend.entities.Usuario;
import cl.desquite.backend.services.IUsuarioService;
import cl.desquite.backend.utils.ResultadoProc;
import cl.desquite.backend.utils.SearchPagination;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioRestController {

	@Autowired
	IUsuarioService usuarioService;

	@PreAuthorize("hasAuthority('USUARIO_VER')")
	@GetMapping("/{id}")
	public ResponseEntity<ResultadoProc<Usuario>> findById(@PathVariable("id") int usuarioId) {
		ResultadoProc<Usuario> salida = usuarioService.findById(usuarioId);
		return new ResponseEntity<ResultadoProc<Usuario>>(salida, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('USUARIO_VER')")
	@GetMapping("/{id}/active")
	public ResponseEntity<ResultadoProc<Usuario>> findByIdAndActiveTrue(@PathVariable("id") int usuarioId) {
		ResultadoProc<Usuario> salida = usuarioService.findByIdAndActivoTrue(usuarioId);
		return new ResponseEntity<ResultadoProc<Usuario>>(salida, HttpStatus.OK);
	}

	// @PreAuthorize("hasAuthority('USUARIO_LISTAR')")
	@PostMapping("/page-all-by-search")
	public ResponseEntity<ResultadoProc<Page<Usuario>>> findAllPaginatedWithSearch(
			@RequestBody SearchPagination<String> searchPagination) {
		PageRequest pageable = searchPagination.getPageRequest();
		String query = searchPagination.getSeek();
		ResultadoProc<Page<Usuario>> salida = usuarioService.findAllPaginatedWithSearch(pageable, query);
		return new ResponseEntity<ResultadoProc<Page<Usuario>>>(salida, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('USUARIO_EDITAR')")
	@GetMapping("/change-state/{id}")
	public ResponseEntity<ResultadoProc<Usuario>> changeState(@PathVariable("id") int usuarioId) {
		ResultadoProc<Usuario> salida = usuarioService.changeState(usuarioId);
		return new ResponseEntity<ResultadoProc<Usuario>>(salida, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('USUARIO_CREAR')")
	@PostMapping
	public ResponseEntity<ResultadoProc<Usuario>> save(@RequestBody Usuario usuario) {
		ResultadoProc<Usuario> salida = usuarioService.save(usuario);
		return new ResponseEntity<ResultadoProc<Usuario>>(salida, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('USUARIO_EDITAR')")
	@PutMapping
	public ResponseEntity<ResultadoProc<Usuario>> update(@RequestBody Usuario usuario) {
		ResultadoProc<Usuario> salida = usuarioService.update(usuario);
		return new ResponseEntity<ResultadoProc<Usuario>>(salida, HttpStatus.OK);
	}

	@PostMapping("/new-token-for-change-password")
	public ResponseEntity<ResultadoProc<Boolean>> createTokenForResetPassword(@RequestBody Usuario usuario) {
		ResultadoProc<Boolean> usuarioToken = this.usuarioService.createTokenForResetPassword(usuario);
		return new ResponseEntity<ResultadoProc<Boolean>>(usuarioToken, HttpStatus.OK);
	}

}
