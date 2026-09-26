package com.tiago.relatos_seguranca_api.services;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.tiago.relatos_seguranca_api.infrastructure.dto.response.RelatoResponse;
import com.tiago.relatos_seguranca_api.infrastructure.dto.response.UsuarioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PdfService {

    private final RelatoService relatoService;
    private final UsuarioService usuarioService;

    public Resource gerarPdfRelatos() {

        List<RelatoResponse> relatos = relatoService.findAll();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Document document = new Document();

        try {
            PdfWriter.getInstance(document, outputStream);

            document.open();

            Font tituloFont = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    18
            );

            Font textoFont = FontFactory.getFont(
                    FontFactory.HELVETICA,
                    10
            );

            Paragraph titulo = new Paragraph(
                    "Relatos de Segurança",
                    tituloFont
            );

            titulo.setAlignment(Element.ALIGN_CENTER);

            document.add(titulo);

            document.add(new Paragraph(" "));

            for (RelatoResponse relato : relatos) {

                document.add(
                        new Paragraph(
                                "ID: " + relato.getId(),
                                textoFont
                        )
                );

                document.add(
                        new Paragraph(
                                "Título: " + relato.getTitulo(),
                                textoFont
                        )
                );

                document.add(
                        new Paragraph(
                                "Descrição: " + relato.getDescricao(),
                                textoFont
                        )
                );

                document.add(
                        new Paragraph(
                                "Funcionário: " + relato.getUsuario().getName(),
                                textoFont
                        )
                );

                document.add(
                        new Paragraph(
                                "E-mail: " + relato.getUsuario().getEmail(),
                                textoFont
                        )
                );

                document.add(
                        new Paragraph(
                                "Data do relato: " + relato.getDataDoRelato(),
                                textoFont
                        )
                );

                document.add(
                        new Paragraph(
                                "Status: " + relato.getStatus(),
                                textoFont
                        )
                );

                document.add(
                        new Paragraph(
                                "Prioridade: " + relato.getPrioridade(),
                                textoFont
                        )
                );

                document.add(new Paragraph(" "));

                document.add(new Paragraph(
                        "----------------------------------------",
                        textoFont
                ));

                document.add(new Paragraph(" "));
            }

        } finally {
            document.close();
        }

        return new ByteArrayResource(
                outputStream.toByteArray()
        );
    }

    public Resource gerarPdfUsuarios() {

        List<UsuarioResponse> usuarios = usuarioService.findAll();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Document document = new Document();

        try {
            PdfWriter.getInstance(document, outputStream);

            document.open();

            Font tituloFont = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    18
            );

            Font textoFont = FontFactory.getFont(
                    FontFactory.HELVETICA,
                    10
            );

            Paragraph titulo = new Paragraph(
                    "Usuários Cadastrados",
                    tituloFont
            );

            titulo.setAlignment(Element.ALIGN_CENTER);

            document.add(titulo);

            document.add(new Paragraph(" "));

            for (UsuarioResponse usuario : usuarios) {

                document.add(
                        new Paragraph(
                                "ID: " + usuario.getId(),
                                textoFont
                        )
                );

                document.add(
                        new Paragraph(
                                "Nome: " + usuario.getName(),
                                textoFont
                        )
                );

                document.add(
                        new Paragraph(
                                "E-mail: " + usuario.getEmail(),
                                textoFont
                        )
                );

                document.add(
                        new Paragraph(
                                "Perfis: " + usuario.getProfiles(),
                                textoFont
                        )
                );

                document.add(new Paragraph(" "));

                document.add(
                        new Paragraph(
                                "----------------------------------------",
                                textoFont
                        )
                );

                document.add(new Paragraph(" "));
            }

        } finally {
            document.close();
        }

        return new ByteArrayResource(
                outputStream.toByteArray()
        );
    }

}
