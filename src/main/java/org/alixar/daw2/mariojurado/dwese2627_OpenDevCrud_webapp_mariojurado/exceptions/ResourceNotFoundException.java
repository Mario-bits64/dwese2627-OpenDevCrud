package org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.exceptions;

/**
 * Excepción que se lanza cuando no se encuentra un recurso.
 *
 * @author Mario
 * @version 1.0
 */
public class ResourceNotFoundException extends RuntimeException {

    private final String resource;
    private final String field;
    private final Object value;

    /**
     * Crea la excepción con el recurso, el campo y el valor buscado.
     */
    public ResourceNotFoundException(String resource, String field, Object value) {
        super(resource + " not found (" + field + "=" + value + ")");
        this.resource = resource;
        this.field = field;
        this.value = value;
    }

    /**
     * Devuelve el recurso que no se ha encontrado (proyecto o usuario).
     */
    public String getResource() {
        return resource;
    }
}
