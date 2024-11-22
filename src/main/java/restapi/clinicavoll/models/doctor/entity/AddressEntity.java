package restapi.clinicavoll.models.doctor.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import restapi.clinicavoll.models.AddressDTO;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {

    private String country;
    private String city;
    private String district;
    private String number;

    public AddressEntity(AddressDTO addressDTO) {
        this.country = addressDTO.country();
        this.city = addressDTO.city();
        this.district = addressDTO.district();
        this.number = addressDTO.number();
    }
}
