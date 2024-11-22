package restapi.clinicavoll.models.patient.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import restapi.clinicavoll.models.address.AddressEntity;
import restapi.clinicavoll.models.patient.dto.PatientDTO;

@Entity
@Table(name = "patients")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phoneNumber;
    private String document;

    @Embedded // Esta anotacion indica que este atributo esta incluido dentro de la misma entidad y no como una tabla diferente.
    @Column(name = "addressDTO")
    private AddressEntity addressEntity;

    public PatientEntity(PatientDTO patientDTO) {
        this.name = patientDTO.name();
        this.email = patientDTO.email();
        this.phoneNumber = patientDTO.phoneNumber();
        this.document = patientDTO.document();
        this.addressEntity = new AddressEntity(patientDTO.addressDTO());
    }
}
