package restapi.vollmed.domain.appointment.bussinesrulesvalidations;

import restapi.vollmed.domain.doctor.SpecialtyDoctor;
import java.time.LocalDateTime;

public record AppointmentValidateDTO(

        Long idDoctor,
        Long idPatient,
        LocalDateTime date,
        SpecialtyDoctor specialtyDoctor
) {
    public AppointmentValidateDTO(Long idDoctor, Long idPatient, LocalDateTime date, SpecialtyDoctor specialtyDoctor) {
        this.idDoctor = idDoctor;
        this.idPatient = idPatient;
        this.date = date;
        this.specialtyDoctor = specialtyDoctor;
    }
}
