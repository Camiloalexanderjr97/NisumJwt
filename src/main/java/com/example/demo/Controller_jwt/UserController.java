package com.example.demo.Controller_jwt;

import com.example.demo.User.Entity.Rol;
import com.example.demo.User.Entity.User;
import com.example.demo.User.Jwt.JwtProvider;
import com.example.demo.User.Login.RolName;
import com.example.demo.User.Service.RolService;
import com.example.demo.User.Service.UserService;
import com.example.demo.User.Util.ValidEmail;
import com.example.demo.dto.JwtDto;
import com.example.demo.dto.Mensaje;
import com.example.demo.dto.NewUser;
import com.example.demo.dto.loginUser;
import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class UserController {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	RolService rolService;

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	private UserService usuarioService;

	@Autowired
	private ValidEmail validEmail;

	public static Log LOG = LogFactory.getLog(UserController.class);
	public static Gson gson = new Gson();

	User u = new User();

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public ResponseEntity<?> nuevo(@Valid @RequestBody NewUser nuevoUser, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return new ResponseEntity(new Mensaje("Campos mal puestos o invalidos"), HttpStatus.BAD_REQUEST);
		if (usuarioService.loadUserByUsername(nuevoUser.getUsername()))
			return new ResponseEntity(new Mensaje("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
		if (!validEmail.isValidEmail(nuevoUser.getEmail()))
			return new ResponseEntity(new Mensaje("Mail invalido"), HttpStatus.BAD_REQUEST);
		if (usuarioService.loadUserByMail(nuevoUser.getEmail()))
			return new ResponseEntity(new Mensaje("Ese Mail ya existe"), HttpStatus.BAD_REQUEST);

		try {
			User user = new User(nuevoUser.getName(), nuevoUser.getUsername(),
					passwordEncoder.encode(nuevoUser.getPassword()));

			Set<Rol> roles = new HashSet<>();
			user.setPhones(nuevoUser.getPhones());
			roles.add(rolService.getRolByName(RolName.ROLE_USER).get());
		
			if (nuevoUser.getRol() != null && nuevoUser.getRol().equalsIgnoreCase("admin"))
				roles.add(rolService.getRolByName(RolName.ROLE_ADMIN).get());
			user.setRoles(roles);

			usuarioService.crearUser(user);
			return new ResponseEntity(new Mensaje("User Guardado"), HttpStatus.CREATED);

		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity(new Mensaje("Fallo"), HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<JwtDto> login(@Valid @RequestBody loginUser login, BindingResult binding) {
		if (binding.hasErrors())
			return new ResponseEntity(new Mensaje("Campos mal puestos o invalidos"), HttpStatus.BAD_REQUEST);
		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwt = jwtProvider.generateToken(auth);
		UserDetails userDe = (UserDetails) auth.getPrincipal();

		usuarioService.setTokenBd(jwt, login.getUsername());
		JwtDto jwtDto = new JwtDto(jwt, userDe.getUsername(), userDe.getAuthorities());

		return new ResponseEntity(jwtDto, HttpStatus.OK);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> listUsers = null;
		try {
			listUsers = usuarioService.getUsers();
			return new ResponseEntity<>(listUsers, HttpStatus.OK);
		} catch (HibernateException e) {
			LOG.info(" Error : " + e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/users/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<User> addUser(@RequestBody User user) {
		User usuario = null;
		try {

			usuario = usuarioService.crearUser(user);

			return new ResponseEntity<>(usuario, HttpStatus.OK);
		} catch (HibernateException e) {
			LOG.error("Error: " + e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/users/buscar/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<User> buscarByID(@PathVariable UUID id) {
		User usuario = null;
		try {
			usuario = usuarioService.buscarById(id);

			return new ResponseEntity<>(usuario, HttpStatus.OK);
		} catch (HibernateException e) {
			LOG.error("Error: " + e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/users/eliminar/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public boolean deleteUser(@PathVariable UUID id) {
		boolean resultado = false;
		try {
			resultado = usuarioService.deleteUser(id);

		} catch (HibernateException e) {
			LOG.error(" Error : " + e.getMessage());
		}
		return resultado;
	}

	@RequestMapping(value = "/users/editar/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> editarUser(@Valid @RequestBody NewUser nuevoUser, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return new ResponseEntity(new Mensaje("Campos mal puestos o invalidos"), HttpStatus.BAD_REQUEST);

		User user = new User(nuevoUser.getId(), nuevoUser.getName(), nuevoUser.getUsername(),
				passwordEncoder.encode(nuevoUser.getPassword()));

		Set<Rol> roles = new HashSet<>();

		if (nuevoUser.getRol() != null && nuevoUser.getRol().equalsIgnoreCase("admin")) {
			usuarioService.actualizarRol(user.getId());
		}
		// roles.add(rolService.getRolByName(RolName.ROLE_ADMIN).get());
		// user.setRoles( roles);

		usuarioService.editarUser(user);
		return new ResponseEntity(new Mensaje("User Actualizado"), HttpStatus.CREATED);

	}

}
