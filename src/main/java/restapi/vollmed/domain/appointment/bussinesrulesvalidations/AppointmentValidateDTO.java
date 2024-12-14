package restapi.vollmed.domain.appointment.bussinesrulesvalidations;

import java.time.LocalDateTime;
import restapi.vollmed.domain.doctor.SpecialtyDoctor;

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
