package com.soulware.user_service_back.domain.user.entity;

import com.soulware.user_service_back.domain.town.entity.Town;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String password;

    @ManyToOne
    private Town town;

    public User(String email, Town town) {
        this.email = email;
        this.town = town;
    }

    public User(String email, String password, Town town) {
        this.email = email;
        this.password = password;
        this.town = town;
    }

}
