package ru.kata.spring.boot_security.demo.services;


import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;

   @Autowired
   public UserServiceImpl(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
      this.userRepository = userRepository;
      this.passwordEncoder = passwordEncoder;
   }

   @Override
   public List<User> getAllUsers() {
      return userRepository.findAll();
   }

   @Override
   public void createOrUpdateUser(User user) {

      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userRepository.save(user);
   }

   @Override
   public void deleteUser(int id) {
      userRepository.deleteById(id);
   }

   @Override
   public User getUserById(int id) {
      return userRepository.findById(id).orElse(null);
   }

   @Override
   public User getUserByUsername(String name) {
      return userRepository.findByUsername(name);
   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

      User user = getUserByUsername(username);
      if (user == null) {
         throw new UsernameNotFoundException(String.format("User %s not found", username));
      }
      return user;
   }
}
