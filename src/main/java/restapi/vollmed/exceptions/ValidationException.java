package restapi.vollmed.exceptions;

public class ValidationException extends RuntimeException {

    // Excepcion creada para ser lanzada en caso de no encontrar
    // un registro en la base de datos
    public ValidationException(String message) {
        super(message);
    }
}
