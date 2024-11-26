package restapi.clinicavoll.models.doctor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import restapi.clinicavoll.models.address.AddressEntity;
import restapi.clinicavoll.models.doctor.dto.DoctorCreateDTO;
import restapi.clinicavoll.models.doctor.dto.DoctorUpdateDTO;
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

    // Este atributo es para indicar si el estado del medico es activo o inactivo.
    // Las operaciones de Delete en la base de datos no eliminan el registro, sino que mofifican el
    // el estado de este atributo a false.
    private Boolean activeStatus;

    public DoctorEntity(DoctorCreateDTO doctorCreateDTO) {
        this.name = doctorCreateDTO.name();
        this.email = doctorCreateDTO.email();
        this.phoneNumber = doctorCreateDTO.phoneNumber();
        this.document = doctorCreateDTO.document();
        this.specialtyDoctorDTO = doctorCreateDTO.specialtyDTO();
        this.addressEntity = new AddressEntity(doctorCreateDTO.addressDTO());
        this.activeStatus = true;
    }

    public void updatedData(DoctorUpdateDTO doctorUpdateDTO) {

        // Para verificar si alguno de los campos no necesita actualziarce.
        if (doctorUpdateDTO.name() != null) {
            this.name = doctorUpdateDTO.name();
        }
        if (doctorUpdateDTO.email() != null) {
            this.email = doctorUpdateDTO.email();
        }
        if (doctorUpdateDTO.phoneNumber() != null) {
            this.phoneNumber = doctorUpdateDTO.phoneNumber();
        }
        if (doctorUpdateDTO.document() != null) {
            this.document = doctorUpdateDTO.document();
        }
        if (doctorUpdateDTO.specialtyDoctorDTO() != null) {
            this.specialtyDoctorDTO = doctorUpdateDTO.specialtyDoctorDTO();
        }
        if (doctorUpdateDTO.addressDTO() != null) {
            this.addressEntity = addressEntity.updatedData(doctorUpdateDTO.addressDTO());
        }
    }

    public void deactivateDoctor() {
        this.activeStatus = false;
    }
}
