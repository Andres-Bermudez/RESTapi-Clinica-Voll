package restapi.vollmed.domain.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {

    private String country;
    private String city;
    private String district;
    private String number;

    // Constructor
    public AddressEntity(AddressDTO addressDTO) {
        this.country = addressDTO.country();
        this.city = addressDTO.city();
        this.district = addressDTO.district();
        this.number = addressDTO.number();
    }

    // Metodo Comun
    public AddressEntity updatedData(AddressDTO addressDTO) {
        this.country = addressDTO.country();
        this.city = addressDTO.city();
        this.district = addressDTO.district();
        this.number = addressDTO.number();
        return this;
    }
}
