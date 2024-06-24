package com.example.demo.User.Service;

import com.example.demo.User.Entity.User;
import com.example.demo.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @author camil
 *
 */
@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;

	// public UserModel getUsusarioUsername(String username);

	public List<User> getUsers() {
		try {
			List<User> listadoUser = userRepository.findAll();

			return listadoUser;

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return null;
		}
	}

	public boolean deleteUser(UUID id) {
		boolean resultado = false;
		try {
			userRepository.deleteById(id);
			resultado = true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return resultado;
	}

	public User crearUser(User user) {

		try {
			user.setId(UUID.randomUUID());
			user.setCreated(LocalDateTime.now());
			user.setModified(LocalDateTime.now());
			user.setLastLogin(LocalDateTime.now());
			user.setActive(true);
			System.out.println(user);
			return userRepository.save(user);

		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public int setTokenBd(String token, String username) {
		try {
			return userRepository.updateToken(token, userRepository.findByUsername(username).get().getId(),
					LocalDateTime.now(), LocalDateTime.now());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return 0;
		}
	}

	public boolean editarUser(User user) {

		boolean resultado = false;
		if (userRepository.updateUserSetStatusForNameNative(user.getName(), user.getPassword(), user.getUsername(),
				user.getId(),user.getMail(), LocalDateTime.now()) != 1) {
			resultado = true;
		}

		return resultado;
	}

	public void actualizarRol(UUID id) {
		userRepository.insert(id);

	}

	public boolean loadUserByUsername(String username) {

		try {

			if (userRepository.existsByUsername(username)) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public boolean loadUserByMail(String mail) {

		try {

			if (userRepository.existsByMail(mail)) {
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public boolean existByUsername(String nombre) {
		return userRepository.existsByUsername(nombre);
	}

	public void save(User user) {
		try {
			userRepository.save(user);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public User findById(UUID id) {
		try {
			return userRepository.findById(id).get();

		} catch (Exception e) {
			return null;
		}
	}

}
