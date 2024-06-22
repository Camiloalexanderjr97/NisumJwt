package com.example.demo.User.Entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
    @GeneratedValue(generator = "UUID")
	private UUID id;

	@NotNull
	private String name;

	@NotNull
	@Column(unique = true)
	private String username;

	@NotNull
	private String password;

	private String token;
	private String mail;
	private LocalDateTime created;
	private LocalDateTime modified;
	private LocalDateTime lastLogin;
	private boolean isActive;

	@NotNull
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
	private Set<Rol> roles = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Phone> phones= new HashSet<>();

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}

	
	public Set<Phone> getPhones() {
		return phones;
	}

	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
	}



	public User(@NotNull String name, @NotNull String username, @NotNull String password) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
	}
	public User(@NotNull UUID id,@NotNull String name, @NotNull String username, @NotNull String password) {
		super();
		this.id=id;
		this.name = name;
		this.username = username;
		this.password = password;
	}


}