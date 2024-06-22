package com.example.demo.User.Service;

import com.example.demo.User.Entity.Rol;
import com.example.demo.User.Login.RolName;
import com.example.demo.User.Repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RolService {
    
	@Autowired
	RolRepository rolRepository;
    // public abstract UserModel getUsusarioUsername(String username);

   

    public Optional<Rol> getRolByName(RolName nombre){
    	return rolRepository.findByRolName(nombre);
    }
    
    public void save(Rol rol)
    {
    	rolRepository.save(rol);
    }
    
}
