package com.company.service;

import com.company.model.Role;
import com.company.model.User;
import com.company.repository.RoleRepository;
import com.company.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
    public class UserService implements UserDetailsService {
        @PersistenceContext
        private EntityManager em;
        @Autowired
        UserRepository userRepository;
//        @Autowired
//        RoleRepository roleRepository;
        @Autowired
        BCryptPasswordEncoder bCryptPasswordEncoder;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            return user;
        }

        public User findUserById(UUID userId) {
            User userFromDb = userRepository.findById(userId);
            return userFromDb;
        }

        public List<User> allUsers() {
            return userRepository.findAll();
        }

        public boolean saveUser(User user) {
//            User userFromDB = userRepository.findByUsername(user.getUsername());

//            if (userFromDB != null) {
//                return false;
         //   }

            user.setRoles(Collections.singleton(new Role(UUID.randomUUID(), "ROLE_USER")));
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.saveUser(user);
            return true;
        }

        public boolean deleteUser(UUID userId) {
//            if (userRepository.findById(userId).isPresent()) {
                userRepository.delete(userId);
//                return true;
//            }
            return true;
        }

        public List<User> usergtList(Long idMin) {
            return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                    .setParameter("paramId", idMin).getResultList();
        }
    }
