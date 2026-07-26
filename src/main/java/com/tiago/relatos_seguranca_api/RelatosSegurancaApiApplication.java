package com.tiago.relatos_seguranca_api;

import com.tiago.relatos_seguranca_api.infrastructure.entity.Usuario;
import com.tiago.relatos_seguranca_api.infrastructure.enums.ProfileEnum;
import com.tiago.relatos_seguranca_api.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;
import java.util.TimeZone;

@SpringBootApplication
@RequiredArgsConstructor
public class RelatosSegurancaApiApplication {

	private final UsuarioRepository usuarioRepository;

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(RelatosSegurancaApiApplication.class, args);
	}

	@Bean
	CommandLineRunner carregarUsuarios() {
		return args -> {

			if (usuarioRepository.count() > 0) {
				return;
			}

			Usuario admin = Usuario.builder()
					.name("Administrador")
					.email("admin@email.com")
					.password("123456")
					.profiles(Set.of(ProfileEnum.ROLE_ADMIN))
					.build();

			Usuario tecnico = Usuario.builder()
					.name("Técnico")
					.email("tecnico@email.com")
					.password("123456")
					.profiles(Set.of(ProfileEnum.ROLE_TECHNICIAN))
					.build();

			Usuario colaborador = Usuario.builder()
					.name("Colaborador")
					.email("colaborador@email.com")
					.password("123456")
					.profiles(Set.of(ProfileEnum.ROLE_CUSTOMER))
					.build();

			usuarioRepository.saveAll(List.of(admin, tecnico, colaborador));

			System.out.println(">>> Usuários de teste criados com sucesso!");
		};
		/*SELECT
		r.id,
				r.titulo,
				r.descricao,
				r.status,
				r.prioridade,
				r.data_do_relato,
				u.id AS usuario_id,
		u.name,
				u.email
		FROM relatos r
		INNER JOIN usuarios u
		ON r.usuario_id = u.id
		ORDER BY r.id;*/

	}
}
