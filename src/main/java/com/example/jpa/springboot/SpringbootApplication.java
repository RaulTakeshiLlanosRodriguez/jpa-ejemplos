package com.example.jpa.springboot;

import com.example.jpa.springboot.entities.Person;
import com.example.jpa.springboot.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class SpringbootApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository personRepository;
	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		deleteByObject();
	}

	@Transactional
	public void create(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el nombre: ");
		String name = scanner.next();
		System.out.println("Ingrese el apellido: ");
		String lastname = scanner.next();
		System.out.println("Ingrese el lenguaje de programación: ");
		String programmingLanguage = scanner.next();
		scanner.close();
		Person person = new Person(null, name,lastname,programmingLanguage);
		Person personCreated = personRepository.save(person);
		System.out.println(personCreated);

		personRepository.findById(personCreated.getId()).ifPresent(System.out::println);
	}

	@Transactional
	public void delete(){
		personRepository.findAll().forEach(System.out::println);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id a eliminar: ");
		Long id = scanner.nextLong();
		personRepository.deleteById(id);

		personRepository.findAll().forEach(System.out::println);
		scanner.close();
	}
	@Transactional
	public void deleteByObject(){
		personRepository.findAll().forEach(System.out::println);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id a eliminar: ");
		Long id = scanner.nextLong();

		Optional<Person> personOptional = personRepository.findById(id);
		personOptional.ifPresentOrElse(personRepository::delete,() -> System.out.println("No existe la persona con ese id"));

		personRepository.findAll().forEach(System.out::println);
		scanner.close();
	}

	@Transactional
	public void update(){

		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona: ");
		Long id = scanner.nextLong();
		Optional<Person> personOptional = personRepository.findById(id);
		personOptional.ifPresent(person -> {
			System.out.println(person);
			System.out.println("Ingrese el lenguaje de programación: ");
			String programmingLanguage = scanner.next();
			person.setProgrammingLanguage(programmingLanguage);
			Person personUpdate = personRepository.save(person);
			System.out.println(personUpdate);
		});
		scanner.close();
	}

	//readOnly es utilizado en metodos que hacen consultas sin modificar la base de datos
	@Transactional(readOnly = true)
	public void findOne(){
		/*Person person = null;
		Optional<Person> optionalPerson = personRepository.findById(1L);
		if(optionalPerson.isPresent()){
			person = personRepository.findById(1L).orElseThrow();
		}
		System.out.println(person);*/
		personRepository.findByNameContaining("se").ifPresent(System.out::println);
	}

	@Transactional(readOnly = true)
	public void list(){
		//List<Person> persons = (List<Person>) personRepository.findAll();
		//List<Person> persons = personRepository.findByProgrammingLanguage("Java");
		List<Person> persons = personRepository.buscarByProgrammingLanguage("Java");
		persons.stream().forEach(System.out::println);

		List<Object[]> personsValues = personRepository.obtenerPersonData();
		personsValues.stream().forEach(person -> {
			System.out.println(person[0] +" es experto en "+person[1]);
		});
	}
}
