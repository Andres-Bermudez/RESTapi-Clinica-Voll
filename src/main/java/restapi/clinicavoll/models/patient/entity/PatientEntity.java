package restapi.clinicavoll.models.patient.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import restapi.clinicavoll.models.address.AddressEntity;
import restapi.clinicavoll.models.patient.dto.PatientReceiveDTO;

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
    @Column(name = "address")
    private AddressEntity addressEntity;

    public PatientEntity(PatientReceiveDTO patientReceiveDTO) {
        this.name = patientReceiveDTO.name();
        this.email = patientReceiveDTO.email();
        this.phoneNumber = patientReceiveDTO.phoneNumber();
        this.document = patientReceiveDTO.document();
        this.addressEntity = new AddressEntity(patientReceiveDTO.addressDTO());
    }
}
