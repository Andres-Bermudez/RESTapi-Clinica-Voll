package restapi.clinicavoll.models.patient.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import restapi.clinicavoll.models.address.AddressEntity;
import restapi.clinicavoll.models.patient.dto.PatientCreateDTO;
import restapi.clinicavoll.models.patient.dto.PatientUpdateDTO;

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

    // Este atributo es para indicar si el estado del medico es activo o inactivo.
    // Las operaciones de Delete en la base de datos no eliminan el registro, sino que mofifican el
    // el estado de este atributo a false.
    private Boolean activeStatus;

    public PatientEntity(PatientCreateDTO patientCreateDTO) {
        this.name = patientCreateDTO.name();
        this.email = patientCreateDTO.email();
        this.phoneNumber = patientCreateDTO.phoneNumber();
        this.document = patientCreateDTO.document();
        this.addressEntity = new AddressEntity(patientCreateDTO.addressDTO());
        this.activeStatus = true;
    }

    public void updateData(PatientUpdateDTO patientUpdateEntity) {
        // Para verificar si alguno de los campos no necesita actualziarce.
        if (patientUpdateEntity.name() != null) {
            this.name = patientUpdateEntity.name();
        }
        if (patientUpdateEntity.email() != null) {
            this.email = patientUpdateEntity.email();
        }
        if (patientUpdateEntity.phoneNumber() != null) {
            this.phoneNumber = patientUpdateEntity.phoneNumber();
        }
        if (patientUpdateEntity.document() != null) {
            this.document = patientUpdateEntity.document();
        }
        if (patientUpdateEntity.addressDTO() != null) {
            this.addressEntity = addressEntity.updatedData(patientUpdateEntity.addressDTO());
        }
    }

    public void deactivatePatient() {
        this.activeStatus = false;
    }
}
