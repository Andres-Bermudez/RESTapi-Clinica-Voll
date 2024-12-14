package restapi.vollmed.exceptions;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/*
 * MANEJADOR GLOBAL DE EXCEPCIONES.
 *
 * Esta clase es para tratar los errores que se puedan generar en
 * en la aplicacion a nivel general. Mapea las excepciones lanzadas
 * por los controladores y las trata a nivel global.
*/
@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
    // Registrar la excepción para propósitos de depuración
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception e) {
        e.printStackTrace();
        e.getMessage();
        // Retornar un 500 Internal Server Error para todas las excepciones no manejadas.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
    }
     */

    // En el caso de que el cliente realice una solicitud para obtener un
    // registro de la base de datos y este no exista retorna un 404 en lugar de un 500
    // y de esta manera indicarle al cliente que es un error en su busqueda y no un
    // error en el servidor.
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleError500() {
        // Este metodo retorna un error 404 NOT FOUND, en caso de que
        // detecte que se lanza una EntityNotFoundException.
        return ResponseEntity.notFound().build();
    }

    // Con este metodo mapeamos el error 400 y le retornamos al usuario
    // el error en un formato mas entendible;
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleError400(MethodArgumentNotValidException e) {
        // Lista completa de los errores obtenidos, mapeados en un
        // formato mas legible.
        List<FieldErrorDTO> fieldError = e.getFieldErrors().stream()
                .map(FieldErrorDTO::new).toList();

        // Este metodo retorna un error 404 NOT FOUND, en caso de que
        // detecte que se lanza una EntityNotFoundException.
        return ResponseEntity.badRequest().body(fieldError);
    }

    // Maneja ValidationException y retorna un 400 Bad Request.
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handleValidationException(ValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    // Este es el tipo de objeto al que mapeamos el error obtenido para que
    // sea mas legible.
    public record FieldErrorDTO(
            // Atributos del objeto.
            String field,
            String error
    ) {
        public FieldErrorDTO(FieldError fieldError) {
            // Constructor para inicializar los atributos.
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}
