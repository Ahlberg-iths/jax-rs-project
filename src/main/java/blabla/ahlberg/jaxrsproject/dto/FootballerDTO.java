package blabla.ahlberg.jaxrsproject.dto;

import jakarta.validation.constraints.NotNull;

public record FootballerDTO(@NotNull String name, @NotNull String nationality, @NotNull String club){
}
