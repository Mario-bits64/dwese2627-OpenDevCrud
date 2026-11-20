package org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.exceptions;

/**
 * Excepción que se lanza cuando ya existe un recurso con un valor que debe ser único.
 *
 * @author Mario
 * @version 1.0
 */
public class DuplicateResourceException extends RuntimeException {

    private final String resource;
    private final String field;
    private final Object value;

    /**
     * Crea la excepción con el recurso, el campo y el valor duplicado.
     */
    public DuplicateResourceException(String resource, String field, Object value) {
        super("Duplicate " + resource + " (" + field + "=" + value + ")");
        this.resource = resource;
        this.field = field;
        this.value = value;
    }
}
