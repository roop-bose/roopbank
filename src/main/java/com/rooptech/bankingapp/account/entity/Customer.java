package com.rooptech.bankingapp.account.entity;
import com.rooptech.bankingapp.auth.entity.Role;
import com.rooptech.bankingapp.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    @Column(nullable = false,length = 150)
    private String customerName;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false,unique = true, length = 10)
    private String mobileNumber;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id" )
    private Account account;
    @Column(nullable = false ,length = 100)
    private String hashedPassword;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "customer_role",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}
