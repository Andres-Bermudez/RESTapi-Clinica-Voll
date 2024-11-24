package restapi.clinicavoll.models.doctor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import restapi.clinicavoll.models.address.AddressEntity;
import restapi.clinicavoll.models.doctor.dto.DoctorReceiveDTO;
import restapi.clinicavoll.models.doctor.dto.SpecialtyDoctorDTO;

@Table(name = "doctors")
@Entity()
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phoneNumber;
    private String document;

    @Enumerated(EnumType.STRING)
    @Column(name = "specialty")
    private SpecialtyDoctorDTO specialtyDoctorDTO;

    @Embedded // Esta anotacion indica que este atributo esta incluido dentro de la misma entidad y no como una tabla diferente.
    @Column(name = "addressDTO")
    private AddressEntity addressEntity;

    public DoctorEntity(DoctorReceiveDTO doctorReceiveDTO) {
        this.name = doctorReceiveDTO.name();
        this.email = doctorReceiveDTO.email();
        this.phoneNumber = doctorReceiveDTO.phoneNumber();
        this.document = doctorReceiveDTO.document();
        this.specialtyDoctorDTO = doctorReceiveDTO.specialtyDTO();
        this.addressEntity = new AddressEntity(doctorReceiveDTO.addressDTO());
    }
}
