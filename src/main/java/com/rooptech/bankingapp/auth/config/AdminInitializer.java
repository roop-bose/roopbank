package com.rooptech.bankingapp.auth.config;

import com.rooptech.bankingapp.account.entity.Customer;
import com.rooptech.bankingapp.account.repository.CustomerRepository;
import com.rooptech.bankingapp.auth.entity.Role;
import com.rooptech.bankingapp.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        String adminEmail = "admin2026@gmail.com";

        // Check whether admin already exists
        if (customerRepository
                .findByEmail(adminEmail)
                .isPresent()) {
            return;
        }

        // Find ADMIN role
        Role adminRole = roleRepository
                .findByRoleName("ROLE_ADMIN")
                .orElseThrow(() ->
                        new RuntimeException("ROLE_ADMIN not found")
                );

        // Create admin
        Customer admin = new Customer();

        admin.setCustomerName("System Admin");
        admin.setEmail(adminEmail);
        admin.setMobileNumber("9521567626");

        admin.setHashedPassword(
                passwordEncoder.encode("RoopBank@Admin#2026!X7")
        );

        admin.getRoles().add(adminRole);

        customerRepository.save(admin);
    }
}