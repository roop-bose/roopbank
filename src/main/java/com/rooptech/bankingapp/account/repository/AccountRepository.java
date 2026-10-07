package com.rooptech.bankingapp.account.repository;
import com.rooptech.bankingapp.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

}
