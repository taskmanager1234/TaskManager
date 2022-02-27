package com.company.model;


import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "user_t")
@NamedQueries({
        @NamedQuery(name="User.findAllWithRoles",
                query = "select distinct u from User u left join fetch u.roles")
})
public class User implements UserDetails {

    @Id
    @Column(name = "user_id", columnDefinition = "varchar(40)")
    //@GeneratedValue()
   // @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password")
    private String password;

    @Transient
    private String passwordConfirm;

//    @ManyToMany(/*cascade = CascadeType.PERSIST*/ fetch = FetchType.EAGER)
//    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    public User() {
        this.username = "";
        this.password = "";
        this.id = UUID.randomUUID();
    }

    @OneToMany(fetch = FetchType.EAGER )
    @JoinColumn(name = "user_id", columnDefinition = "varchar(40)")
    private List<TasksJournal> tasksJournals;


    public List<TasksJournal> getTasksJournals() {
        return tasksJournals;
    }

    public void setTasksJournals(List<TasksJournal> tasksJournals) {
        this.tasksJournals = tasksJournals;
    }


    public void setId(UUID user_id) {
        this.id = user_id;
    }

    public User(UUID id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public UUID getId() {
        return id;
    }


}
