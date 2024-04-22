package ru.kata.spring.boot_security.demo.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Pattern(regexp = "[A-Z][a-z]+", message = "Name should consist of letters and start with a capital")
    @NotBlank(message = "Name is a required field")
    @Size(min = 2, max = 40, message = "Name length should be between 2 and 40 symbols")
    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @Pattern(regexp = "[A-Z][a-z]+", message = "Surname should consist of letters and start with a capital")
    @NotBlank(message = "Surname is a required field")
    @Size(min = 2, max = 40, message = "Surname length should be between 2 and 40 symbols")
    @Column(name = "surname", nullable = false, length = 40)
    private String surname;

    @Min(value = 1, message = "Age should be greater than 0")
    @Max(value = 120, message = "I just can't believe that!")
    @Column(name = "age", nullable = false)
    private byte age;

    @Pattern(regexp = "[A-Za-z0-9]+", message = "Username should consist of letters and digits")
    @NotBlank(message = "Username is a required field")
    @Size(min = 2, max = 40, message = "Username length should be between 2 and 40 symbols")
    @Column(name = "username", unique = true, nullable = false, length = 40)
    private String username;

    @Pattern(regexp = "[A-Za-z0-9]+", message = "Password should consist of letters and digits")
    @NotBlank(message = "Password is a required field")
    @Size(min = 6, message = "Password length should be at least 6 symbols")
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User(String name, String surname, byte age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public User() {}

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getType())).collect(Collectors.toList());
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String firstName) {
        this.name = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }


}
