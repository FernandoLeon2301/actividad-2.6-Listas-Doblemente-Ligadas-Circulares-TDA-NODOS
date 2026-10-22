/*
 LEON GAMEZ FERNANDO
 CAMACHO GUERRA JACOBO
 GONZALEZ ALVARADO ALEXA MICHELLE
*/

public record Proceso(int id, String nombre, int rafagaMs) {

    public Proceso {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número entero mayor a 0.");
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del proceso no puede estar vacío.");
        }

        if (rafagaMs <= 0) {
            throw new IllegalArgumentException("La ráfaga en milisegundos debe ser mayor a 0.");
        }
    }

    @Override
    public String toString() {
        return String.format("[ID: %d] %s - Ráfaga: %d ms", id, nombre, rafagaMs);
    }
}