package com.tiago.relatos_seguranca_api.infrastructure.entity;

import com.tiago.relatos_seguranca_api.infrastructure.enums.Prioridade;
import com.tiago.relatos_seguranca_api.infrastructure.enums.StatusRelato;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "relatos")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Relato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    @Column(nullable = false, length = 100)
    private String titulo;

    @NotBlank
    @Size(max = 1000)
    @Column(nullable = false, length = 1000)
    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusRelato status;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    private LocalDateTime dataDoRelato = LocalDateTime.now();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany( mappedBy = "relato", cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Foto> fotos = new ArrayList<>();


}
