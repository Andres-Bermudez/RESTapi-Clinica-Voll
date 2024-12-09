package restapi.vollmed.domain.doctor;

public record DoctorReadDTO(
        Long id,
        String name,
        SpecialtyDoctor specialty,
        String email
) {

    public DoctorReadDTO(DoctorEntity doctorEntity) {
        this(doctorEntity.getId(),
             doctorEntity.getName(),
             doctorEntity.getSpecialtyDoctor(),
             doctorEntity.getEmail());
    }
}
