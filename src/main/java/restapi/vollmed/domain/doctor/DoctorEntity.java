package restapi.vollmed.domain.doctor;

import lombok.Getter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import restapi.vollmed.domain.address.AddressEntity;

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
    private SpecialtyDoctor specialtyDoctor;

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
        this.specialtyDoctor = doctorCreateDTO.specialtyDTO();
        this.addressEntity = new AddressEntity(doctorCreateDTO.addressDTO());
        this.activeStatus = true;
    }

    public DoctorReadDTO updatedData(DoctorUpdateDTO doctorUpdateDTO) {

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
        if (doctorUpdateDTO.specialtyDoctor() != null) {
            this.specialtyDoctor = doctorUpdateDTO.specialtyDoctor();
        }
        if (doctorUpdateDTO.addressDTO() != null) {
            this.addressEntity = addressEntity.updatedData(doctorUpdateDTO.addressDTO());
        }
        // Retorna al cliente el objeto actualizado.
        return new DoctorReadDTO(this);
    }

    public void deactivateDoctor() {
        this.activeStatus = false;
    }
}
