package com.tiago.relatos_seguranca_api.infrastructure.entity;

import com.tiago.relatos_seguranca_api.infrastructure.enums.ProfileEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Size(min = 3, max = 50)
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "usuario_profiles",
            joinColumns = @JoinColumn(name = "usuario_id")
    )
    @Column(name = "profile")
    private Set<ProfileEnum> profiles = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    private List<Relato> relatos = new ArrayList<>();
}