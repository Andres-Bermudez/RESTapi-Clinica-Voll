package restapi.vollmed.domain.appointment;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import static org.mockito.ArgumentMatchers.any;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import restapi.vollmed.domain.doctor.SpecialtyDoctor;
import org.springframework.boot.test.json.JacksonTester;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

// TEST UNITARIO DEL AppointmentController.

@SpringBootTest // Para subir el contexto necesario para el controlador para poder ejecutar los tests.
@AutoConfigureMockMvc // Para poder usar Mocks para hacer simulaciones del controlador.
@AutoConfigureJsonTesters // // Para crear JSONs con Spring a partir de Objetos Java.
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Para que spring cree los JSON por medio del objeto Java.
    @Autowired
    private JacksonTester<AppointmentDTO> jsonScheduledAppointment;

    @Autowired
    private JacksonTester<CancelledAppointmentDTO> jsonCancelledAppointment;

    @Autowired
    private JacksonTester<AppointmentDetailsDTO> jsonResponseScheduleAppointment;

    // Para mocar el servicio de Appointments para que no pase por todos los metodos y evitar
    // que realice acciones que no queremos en el test del controlador como por ejemplo
    // que ejecute una consulta a la base de datos.
    @MockBean
    private AppointmentService appointmentService;

    @Test
    @DisplayName("Return 400 Bad Request: Deberia devolver un 400 bad request porque no se" +
            " envian datos en el cuerpo de la solicitud.")
    @WithMockUser // Para simular que ya estamos autenticados y autorizados en la aplicacion.
    void scheduleAppointmentNotDataBodyTest() throws Exception {
        // Para simular una solicitud  de tipo POST al endpoint /schedule.
        HttpServletResponse response = mockMvc.perform(post("/appointment/schedule"))
                .andReturn().getResponse();

        // Para verificar que el status de la respuesta recibida sea un 400 bad request.
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Return 200 Ok: Deberia devolver un 200 Ok cuando se envian los datos" +
            "correctos en el cuerpo de la solicitud.")
    @WithMockUser // Para simular que ya estamos autenticados y autorizados en la aplicacion.
    void scheduleAppointmentTest() throws Exception {

        // GIVEN O ARRANGE.
        // Fecha en la que se simula el agendamiento de la cita.
        LocalDateTime date = LocalDateTime.now().plusHours(1);
        SpecialtyDoctor specialty = SpecialtyDoctor.CARDIOLOGY;
        AppointmentDetailsDTO appointmentDetailsDTO =
                new AppointmentDetailsDTO(null, 1L, 1L, date);

        // Para que cuando quiera acceder al servicio de Appointments me retorne siempre un
        // JSON preestablecido sin importar el contenido que se le envie.
        // Esto lo hacemos con la libreria Mockito.
        //
        // Cuando appointmentService acceda al metodo scheduleAppointment sin importar el contenido
        // que se le envie, siempre vas a retornar el JSON appointmentsDetailsDTO.
        when(appointmentService.scheduleAppointment(any())).thenReturn(appointmentDetailsDTO);

        // WHEN O ACT.
        // Para simular una solicitud  de tipo POST al endpoint /appointment/schedule.
        var response = mockMvc.perform(post("/appointment/schedule")
                        .contentType(MediaType.APPLICATION_JSON) // Para indicar que vamos a enviar un JSON.
                        .content(jsonScheduledAppointment.write(new AppointmentDTO(
                                // Este es el JSON que enviamos a la solicitud.
                                // Es generado automaticamente con Spring por medio de la entidad Java.
                                    1L,
                                    1L,
                                    date,
                                    specialty)
                                )
                                .getJson()
                        )
                )
                .andReturn()
                .getResponse();

        String jsonResponseExpected = jsonResponseScheduleAppointment.write(appointmentDetailsDTO).getJson();

        // THEN O ASSERT.
        // Para verificar que el status de la respuesta sea un 200 Ok.
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        // Para verificar que el JSON de cuerpo de la respuesta recibida sea el que nosotros esperamos.
        assertThat(response.getContentAsString()).isEqualTo(jsonResponseExpected);
    }

    @Test
    @DisplayName("Return 400 Bad Request: Deberia devolver un 400 Bad Request si no se le" +
            "envian los datos correctos.")
    @WithMockUser
    void errorCancelledAppointmentTest() throws Exception {
        // Para simular una solicitud  de tipo DELETE al endpoint /appointment/cancel.
        HttpServletResponse response = mockMvc.perform(delete("/appointment/cancel"))
                .andReturn().getResponse();

        // Para verificar que el status de la respuesta recibida sea un 400 Bad Request.
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Return 204 No Content: Deberia devolver un 204 No Content si se envian" +
            "los datos correctos y se elimina la cita de forma exitosa.")
    @WithMockUser
    void cancelledAppointmentTest() throws Exception {

        // Para simular una solicitud  de tipo DELETE al endpoint /appointment/cancel.
        var response = mockMvc.perform(delete("/appointment/cancel")
                    .contentType(MediaType.APPLICATION_JSON) // Para indicar que vamos a enviar un JSON.
                    .content(jsonCancelledAppointment.write(new CancelledAppointmentDTO(
                            // Este es el JSON que enviamos a la solicitud.
                            // Es generado automaticamente con Spring por medio de la entidad Java.
                                1L,
                                ReasonsCancellationAppointment.PATIENT_GAVE_UP)
                        )
                        .getJson()
                    )
            )
            .andReturn()
            .getResponse();

        // Para verificar que el status de la respuesta recibida sea un 204 No Content.
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
