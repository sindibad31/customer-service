package fr.grimeh.customerservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository, RepositoryRestConfiguration restConfiguration) {
        return args -> {
            restConfiguration.exposeIdsFor(Customer.class);
            customerRepository.save(new Customer(null, "ENSET", "enset@gmail.com"));
            customerRepository.save(new Customer(null, "IBM", "ibm@gmail.com"));
            customerRepository.save(new Customer(null, "HP", "hp@gmail.com"));

            customerRepository.findAll().forEach(System.out::println);
        };
    }
}

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
}
@RepositoryRestResource
interface CustomerRepository extends JpaRepository<Customer, Long> {}

@Projection(name="p1", types = Customer.class)
interface customerProjection {
    public Long getId();
    public String getName();
}