package com.example.demo.User.Service;

import com.example.demo.User.Entity.Phone;
import com.example.demo.User.Repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PhoneService {
    
	@Autowired
	PhoneRepository phoneRepository;
    // public abstract UserModel getUsusarioUsername(String username);

   

    public Phone getPhoneByNumber(String number){
    	return phoneRepository.findByNumber(number);
    }
    
    public void save(Phone phone)
    {
    	phoneRepository.save(phone);
    }
    
}
