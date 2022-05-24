package com.company.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {
//    @Id
//    @Column(columnDefinition = "varchar(40)")
//    @Type(type = "org.hibernate.type.UUIDCharType")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
   // @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(name = "role_id", columnDefinition = "varchar(40)")
    private UUID id;
    private String name;

//    @Transient
//    @ManyToMany(mappedBy = "roles" /*, cascade = CascadeType.PERSIST*/)
    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private Set<User> users;

    public Role() {
    }

    public Role(UUID id) {
        this.id = id;
    }

    public Role(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}