package com.example.jpa.springboot.repositories;

import com.example.jpa.springboot.entities.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query("SELECT p FROM Person p WHERE p.id=?1")
    Optional<Person> findOne(Long id);

    @Query("SELECT p FROM Person p WHERE p.name=?1")
    Optional<Person> findOneName(String name);

    @Query("SELECT p FROM Person p WHERE p.name LIKE %?1%")
    Optional<Person> findOneLikeName(String name);


    Optional<Person> findByNameContaining(String name);
    List<Person> findByProgrammingLanguage(String programmingLanguage);
    @Query("SELECT p FROM Person p WHERE p.programmingLanguage=?1")
    List<Person> buscarByProgrammingLanguage(String programmingLanguage);

    @Query("SELECT p.name, p.programmingLanguage FROM Person p")
    List<Object[]> obtenerPersonData();
}
