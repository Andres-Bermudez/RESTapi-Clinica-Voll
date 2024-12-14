package restapi.vollmed.domain.doctor;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import static org.mockito.ArgumentMatchers.any;
import restapi.vollmed.domain.address.AddressDTO;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.json.JacksonTester;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest // Para que Spring cargue todo el contexto necesario para testear controladores.
@AutoConfigureMockMvc // Para poder usar Mocks para hacer simulaciones del controlador.
@AutoConfigureJsonTesters // Para crear JSONs con Spring a partir de Objetos Java.
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorService doctorService;

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired
    private JacksonTester<DoctorCreateDTO> jsonCreateDoctor;

    @Autowired
    private JacksonTester<DoctorReadDTO> jsonReadDoctor;

    @Test
    @DisplayName("Return 201 Created: Este metodo debe retornar un 201 Created si se le envian" +
            "los datos correctos en el formato JSON para crear un nuevo medico.")
    @WithMockUser // Para simular que ya estamos autenticados y autorizados.
    void doctorRegistrationTest() throws Exception {

        // GIVEN O ARRANGE.
        // Objeto con el que simulamos el envio de datos a la solicitud.
        DoctorCreateDTO doctorCreateDTO = new DoctorCreateDTO(
                "doctor1",
                "doctor1@email.com",
                "1234567890",
                "87654321",
                SpecialtyDoctor.CARDIOLOGY,
                address()
        );

        DoctorEntity doctorEntity = new DoctorEntity(doctorCreateDTO);

        // Para que cuando quiera acceder al servicio me retorne siempre un JSON preestablecido sin
        // importar el contenido que se le envie.Esto lo hacemos con la libreria Mockito.
        //
        // Cuando doctorRepository acceda al metodo save() sin importar el contenido
        // que se le envie, siempre va a retornar el objeto doctorEntity.
        when(doctorRepository.save(any())).thenReturn(doctorEntity);

        // Para que cuando quiera acceder al servicio me retorne siempre un JSON preestablecido sin
        // importar el contenido que se le envie.Esto lo hacemos con la libreria Mockito.
        //
        // Cuando doctorService acceda al metodo saveDoctor() sin importar el contenido
        // que se le envie, siempre va a retornar el objeto doctorEntity.
        when(doctorService.saveDoctor(any())).thenReturn(doctorEntity);

        // Objeto que se espera recibir en el cuerpo de la respuesta.
        DoctorReadDTO doctorReadDTO = new DoctorReadDTO(doctorEntity);

        // WHEN O ACT.
        // Para simular una solicitud  de tipo POST al endpoint /doctor/registration.
        var response = mockMvc.perform(post("/doctor/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCreateDoctor.write(new DoctorCreateDTO(
                                doctorCreateDTO.name(),
                                doctorCreateDTO.email(),
                                doctorCreateDTO.phoneNumber(),
                                doctorCreateDTO.document(),
                                doctorCreateDTO.specialtyDTO(),
                                address()
                        ))
                        .getJson()
                ))
                .andReturn()
                .getResponse();

        // JSON que esperamos recibir en el cuerpo de la respuesta.
        String jsonExpectedResponse = jsonReadDoctor.write(doctorReadDTO).getJson();

        // THEN O ASSERT.
        // Para verificar que el JSON recibido en la respuesta a la solicitud sea el esperado.
        assertThat(response.getContentAsString()).isEqualTo(jsonExpectedResponse);

        // Para verificar que el status de la respuesta recibida sea un 201 Created.
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    // Para crear una direccion generica para el test.
    private AddressDTO address() {
        return new AddressDTO("c", "c", "d", "n");
    }

    @Test
    @DisplayName("Return 400 Bad Request: Este metodo debe retornar un 400 Bad Request si NO se le envian" +
            "los datos correctos en el formato JSON para crear un nuevo medico.")
    @WithMockUser // Para simular que ya estamos autenticados y autorizados.
    void errorDoctorRegistrationTest() throws Exception {
        // Para simular una solicitud  de tipo POST al endpoint /doctor/registration.
        var response = mockMvc.perform(
                post("/doctor/registration")).andReturn().getResponse();

        // Para verificar que el status de la respuesta recibida sea un 400 Bad Request.
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
