package lk.ijse.gdse.bbms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthCheckupDTO {
    private String checkupId;
    private String donorId;
    private String healthStatus;
    private Date checkupDate;
    private double weight;
    private double sugarLevel;
    private String bloodPressure;
    DonationDTO donation;

    public void setDonation(String text, String campaignId, String checkupId, String bloodGroup, int qty, Date donationDate) {
        this.donation = new DonationDTO(text, campaignId, checkupId, bloodGroup, qty, donationDate);
    }
}