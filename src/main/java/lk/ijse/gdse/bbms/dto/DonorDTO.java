package lk.ijse.gdse.bbms.dto;

import lk.ijse.gdse.bbms.common.BloodGroup;
import lk.ijse.gdse.bbms.common.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DonorDTO {
    private String donorId;
    private String donorName;
    private String donorNic;
    private String donorAddress;
    private String donorEmail;
    private String bloodGroup;
    private String gender;
    private Date dob;
    private Date lastDonationDate;
}
