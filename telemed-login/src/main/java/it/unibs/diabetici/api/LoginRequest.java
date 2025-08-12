package it.unibs.diabetici.api;

import jakarta.validation.constraints.*;

public record LoginRequest(
    @NotBlank @Email String email,
    @NotBlank String password
) {}
