package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kata.spring.boot_security.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("Select u from User u left join fetch u.roles where u.username = :username")
    User findByUsername(@Param("username") String username);
}
