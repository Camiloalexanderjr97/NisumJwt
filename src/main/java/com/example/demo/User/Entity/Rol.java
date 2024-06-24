package com.example.demo.User.Entity;

import com.example.demo.User.Login.RolName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
public class Rol {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RolName rolName;
    
    public Rol(RolName rolName) {
		super();
		this.rolName = rolName;
	}
	public Rol() {
		super();
	}

    
}
