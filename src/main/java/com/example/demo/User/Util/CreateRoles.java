package com.example.demo.User.Util;

import com.example.demo.User.Entity.Rol;
import com.example.demo.User.Login.RolName;
import com.example.demo.User.Service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class CreateRoles implements CommandLineRunner {

	@Autowired
	RolService rolService;
	
	@Override
	public void run(String... args) throws Exception {
		Rol rolAdmin = new Rol(RolName.ROLE_ADMIN);
		Rol rolUser = new Rol(RolName.ROLE_USER);
	rolService.save(rolAdmin);
	rolService.save(rolUser);
		// TODO Auto-generated method stub
		
	}
	

}
