package com.tiago.relatos_seguranca_api.infrastructure.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ProfileEnum {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_CUSTOMER("ROLE_CUSTOMER"),
    ROLE_TECHNICIAN("ROLE_TECHNICIAN");


    private final String descricao;

    public static ProfileEnum toEnum (final String description) {
        return Arrays.stream(ProfileEnum.values())
                .filter(profileEnum -> profileEnum.getDescription().equals(description))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid description: " + description));


    }

    public String getDescription() {
        return descricao;
    }

//    public static ProfileEnum toEnum(String name) {
//
//        if (name == null) {
//            return null;
//        }
//
//        return Arrays.stream(values())
//                .filter(profile -> profile.name().equalsIgnoreCase(name))
//                .findFirst()
//                .orElseThrow(() ->
//                        new IllegalArgumentException("Perfil inválido: " + name));
//    }


}