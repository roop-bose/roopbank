package com.rooptech.bankingapp.account.repository;

import com.rooptech.bankingapp.account.entity.Customer;
import com.rooptech.bankingapp.common.AuditorAwareImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@Import(AuditorAwareImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldFindCustomerByMobileNumber() {

        Customer customer = new Customer();
        customer.setCustomerName("Rahul Sharma");
        customer.setEmail("rahul@gmail.com");
        customer.setMobileNumber("9876543210");
        customer.setHashedPassword("hashedPassword123");

        customerRepository.save(customer);

        Optional<Customer> result =
                customerRepository.findByMobileNumber("9876543210");

        assertThat(result).isPresent();
        assertThat(result.get().getCustomerName())
                .isEqualTo("Rahul Sharma");
        assertThat(result.get().getMobileNumber())
                .isEqualTo("9876543210");
    }

    @Test
    void shouldReturnEmptyWhenMobileNumberDoesNotExist() {

        Optional<Customer> result =
                customerRepository.findByMobileNumber("9876543210");

        assertThat(result).isEmpty();
    }

    @Test
    void shouldFindCustomerByEmail() {

        Customer customer = new Customer();
        customer.setCustomerName("Rahul Sharma");
        customer.setEmail("rahul@gmail.com");
        customer.setMobileNumber("9876543210");
        customer.setHashedPassword("hashedPassword123");

        customerRepository.save(customer);

        Optional<Customer> result =
                customerRepository.findByEmail("rahul@gmail.com");

        assertThat(result).isPresent();
        assertThat(result.get().getEmail())
                .isEqualTo("rahul@gmail.com");
    }

    @Test
    void shouldReturnEmptyWhenEmailDoesNotExist() {

        Optional<Customer> result =
                customerRepository.findByEmail("unknown@gmail.com");

        assertThat(result).isEmpty();
    }

    @Test
    void shouldFindCustomerByEmailOrMobileNumberUsingEmail() {

        Customer customer = new Customer();
        customer.setCustomerName("Rahul Sharma");
        customer.setEmail("rahul@gmail.com");
        customer.setMobileNumber("9876543210");
        customer.setHashedPassword("hashedPassword123");

        customerRepository.save(customer);

        Optional<Customer> result =
                customerRepository.findByEmailOrMobileNumber(
                        "rahul@gmail.com",
                        "9999999999"
                );

        assertThat(result).isPresent();
        assertThat(result.get().getEmail())
                .isEqualTo("rahul@gmail.com");
    }

    @Test
    void shouldFindCustomerByEmailOrMobileNumberUsingMobileNumber() {

        Customer customer = new Customer();
        customer.setCustomerName("Rahul Sharma");
        customer.setEmail("rahul@gmail.com");
        customer.setMobileNumber("9876543210");
        customer.setHashedPassword("hashedPassword123");

        customerRepository.save(customer);

        Optional<Customer> result =
                customerRepository.findByEmailOrMobileNumber(
                        "unknown@gmail.com",
                        "9876543210"
                );

        assertThat(result).isPresent();
        assertThat(result.get().getMobileNumber())
                .isEqualTo("9876543210");
    }

    @Test
    void shouldReturnEmptyWhenEmailAndMobileNumberDoNotExist() {

        Optional<Customer> result =
                customerRepository.findByEmailOrMobileNumber(
                        "unknown@gmail.com",
                        "9999999999"
                );

        assertThat(result).isEmpty();
    }

    @Test
    void shouldFindCustomerByEmailWithRoles() {

        Customer customer = new Customer();
        customer.setCustomerName("Rahul Sharma");
        customer.setEmail("rahul@gmail.com");
        customer.setMobileNumber("9876543210");
        customer.setHashedPassword("hashedPassword123");

        customerRepository.save(customer);

        Optional<Customer> result =
                customerRepository.findByEmailWithRoles(
                        "rahul@gmail.com"
                );

        assertThat(result).isPresent();
        assertThat(result.get().getEmail())
                .isEqualTo("rahul@gmail.com");

        assertThat(result.get().getRoles())
                .isNotNull();
    }

    @Test
    void shouldReturnEmptyWhenFindingByEmailWithRolesAndEmailDoesNotExist() {

        Optional<Customer> result =
                customerRepository.findByEmailWithRoles(
                        "unknown@gmail.com"
                );

        assertThat(result).isEmpty();
    }
}