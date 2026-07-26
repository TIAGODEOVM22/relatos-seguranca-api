package com.tiago.relatos_seguranca_api.infrastructure.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "fotos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "O nome do arquivo é obrigatório.")
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String nomeArquivo;

    @NotBlank(message = "A URL da foto é obrigatória.")
    @Size(max = 500)
    @Column(nullable = false, length = 500)
    private String url;

    @NotBlank(message = "O tipo do arquivo é obrigatório.")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String contentType;

    @NotNull(message = "O tamanho do arquivo é obrigatório.")
    @Positive(message = "O tamanho deve ser maior que zero.")
    @Column(nullable = false)
    private Long tamanho;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relato_id", nullable = false)
    private Relato relato;
}
