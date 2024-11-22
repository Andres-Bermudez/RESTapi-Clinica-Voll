package restapi.clinicavoll.models.doctor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import restapi.clinicavoll.models.doctor.dto.SpecialtyDoctorDTO;
import restapi.clinicavoll.models.doctor.dto.DoctorDTO;

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
    private String document;

    @Enumerated(EnumType.STRING)
    @Column(name = "specialty")
    private SpecialtyDoctorDTO specialtyDoctorDTO;

    @Embedded // Esta anotacion indica que este atributo esta incluido dentro de la misma entidad y no como una tabla diferente.
    @Column(name = "address")
    private AddressEntity addressEntity;

    public DoctorEntity(DoctorDTO doctorDTO) {
        this.name = doctorDTO.name();
        this.email = doctorDTO.email();
        this.document = doctorDTO.document();
        this.specialtyDoctorDTO = doctorDTO.specialty();
        this.addressEntity = new AddressEntity(doctorDTO.address());
    }
}
