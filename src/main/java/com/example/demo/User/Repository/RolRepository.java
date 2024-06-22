package com.example.demo.User.Repository;

import com.example.demo.User.Entity.Rol;
import com.example.demo.User.Login.RolName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("rolRepository")
public interface RolRepository extends JpaRepository<Rol, Long> {

	Optional<Rol> findByRolName(RolName rolName);

	// @Query(value = "SELECT new com.Model.RolModel (r.id AS id, "
	// 		+ " r.name AS name ) "
	// 		+ " FROM Rol r INNER JOIN User u ON   r.id = u.rol.id")
	// List<RolModel> getAllRol();
}
