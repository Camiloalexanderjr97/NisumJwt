package com.example.demo.User.Repository;

import com.example.demo.User.Entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PhoneRepository extends JpaRepository<Phone, Long> { 

    @Modifying
	  @Query(value = "  SELECT FROM phone WHERE VALUES (:number)",
//			  update Users u set u.status = ? where u.name = )
	    nativeQuery = true)
	  Phone findByNumber(String number);
    
}
