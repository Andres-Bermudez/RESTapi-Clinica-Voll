package restapi.vollmed.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import restapi.vollmed.domain.user.UserRepository;


// La interfaz UserDetailsService en Spring Security es una parte central del sistema
// de autenticación. Su propósito principal es proporcionar una forma de recuperar los
// detalles del usuario desde una fuente de datos, como una base de datos, un servicio
// web, etc., en función del nombre de usuario proporcionado durante el proceso de autenticación.
@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // La interfaz UserDetailsService tiene un solo método que necesita ser implementado:
    // Este método toma un nombre de usuario como parámetro y devuelve un objeto
    // UserDetails que contiene la información del usuario correspondiente. Si no se
    // encuentra un usuario con el nombre de usuario proporcionado, debe lanzar una
    // excepción UsernameNotFoundException.
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepository.findByUserName(userName);
    }
}
