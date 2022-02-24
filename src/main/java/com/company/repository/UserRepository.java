package com.company.repository;


import com.company.model.Role;
import com.company.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Repository
public class UserRepository implements UserDetailsService {
    @Autowired
    EntityManager entityManager;

    public User findByUsername(String username){
        try {
            User user = (User) entityManager.createQuery(
                    "select distinct u from User u left join fetch u.roles where u.username = :name")
                    .setParameter("name", username).getSingleResult();
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }


//    public List<User> findAll() {
//        return entityManager.createNamedQuery("User.findAllWithRoles", User.class).getResultList();
//    }

    @Transactional
    public void add(User entity) {
        entityManager.persist(entity);
        Role role =(Role) entityManager.createQuery("SELECT r from Role r where r.name = :name")
                .setParameter("name", "ROLE_USER").getSingleResult();
        entityManager.createNativeQuery("insert into user_role(role_id, user_id) values(:role_id, :id)")
                .setParameter("role_id", role.getId())
                .setParameter("id", entity.getId()).executeUpdate();
    }

    @Transactional
    public void delete(UUID id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }


    public List<User> findByName(String username) {

        // ?...
        try {
            // TODO
            List<User> user = entityManager.createQuery(
                    "select distinct u from User u left join fetch u.roles where u.username = :name")
                    .setParameter("name", username).getResultList();
            return user;
        } catch (NoResultException e) {
            // или вернуть пустую коллекцию
            return null;
        }
    }

    public int size() {
        return (int) entityManager.createNativeQuery("SELECT count(*) FROM user", Integer.class).getSingleResult();
    }

    public List<User> findByContains(String name) {
        return null;
    }

    public User findById(UUID id){
       return entityManager.find(User.class, id);
    }
    @Transactional
    public boolean saveUser(User user) {
        String userName = user.getUsername();
        if (!(isUserExist(userName))) {
            add(user);
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        List<User> users = findByName(userName);
        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user not found");
        }
        return users.get(0);
    }

    public boolean isUserExist(String userName) {
        BigInteger count =
                (BigInteger) entityManager.createNativeQuery("Select count(*) from user_t where username = :username")
                        .setParameter("username", userName).getSingleResult();
        return !BigInteger.ZERO.equals(count);
   }

}
