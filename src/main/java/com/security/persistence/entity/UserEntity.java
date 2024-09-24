package com.security.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    @Column(name = "is_enabled")
    private boolean isenabled; /*requerido por springsecurity*/

    @Column(name = "account_no_locked")
    private boolean accountNoLocked; /*requerido por springsecurity*/

    @Column(name = "account_no_expired")
    private boolean accountNoExpired; /*requerido por springsecurity*/

    @Column(name = "credential_no_expired")
    private boolean credentialNoExpired; /*requerido por springsecurity*/

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

}
