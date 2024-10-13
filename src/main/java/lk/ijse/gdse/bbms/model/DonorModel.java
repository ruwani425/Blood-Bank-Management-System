package lk.ijse.gdse.bbms.model;

import lk.ijse.gdse.bbms.common.BloodGroup;
import lk.ijse.gdse.bbms.common.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonorModel implements Serializable {
    private String donorId;
    private String donorName;
    private Date dob;
    private Gender gender;
    private BloodGroup bloodGroup;
    private String email;
    private String address;
    private String nic;
}
