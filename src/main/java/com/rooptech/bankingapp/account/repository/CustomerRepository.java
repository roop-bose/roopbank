package com.rooptech.bankingapp.account.repository;
import com.rooptech.bankingapp.account.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
      Optional<Customer> findByMobileNumber(String mobileNumber);
      @Query("""
        SELECT DISTINCT c
         FROM Customer c
        LEFT JOIN FETCH c.roles
        WHERE c.email = :email
        """)
      Optional<Customer> findByEmailWithRoles(
              @Param("email") String email
      );
      Optional<Customer> findByEmail(String email);
      Optional<Customer> findByEmailOrMobileNumber(
              String email,
              String mobileNumber
      );
      // added because of kafka impl
      Optional<Customer> findByAccountAccountId(Long accountId);
}
