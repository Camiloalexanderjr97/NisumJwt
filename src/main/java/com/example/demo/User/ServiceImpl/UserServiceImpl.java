package com.example.demo.User.ServiceImpl;


import com.example.demo.User.Entity.User;
import com.example.demo.User.Repository.UserRepository;
import com.example.demo.User.Util.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service()
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository usuarioRepository;




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
       User user =usuarioRepository.findByUsername(username).get();

        return UserPrincipal.build(user);
    }


    // @Override
    // public UserModel getUsusarioUsername(String username) {
    // UserModel user = usuarioRepository.findByUserUsername(username);
    // return user;
    // }

}
