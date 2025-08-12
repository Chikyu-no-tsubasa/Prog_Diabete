package it.unibs.diabetici.api;

public record LoginResponse(
    String token,
    String role,
    Long pazienteId,
    Long diabetologoId
) {}
