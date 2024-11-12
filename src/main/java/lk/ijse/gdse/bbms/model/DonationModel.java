package lk.ijse.gdse.bbms.model;

import lk.ijse.gdse.bbms.dto.DonationDTO;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DonationModel {
    public boolean addDonation(DonationDTO donationDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into Blood_donation values (?,?,?,?,?,?)",
                donationDTO.getDonation_id(),
                donationDTO.getBlood_campaign_id(),
                donationDTO.getHealth_checkup_id(),
                donationDTO.getBlood_group(),
                donationDTO.getQty(),
                donationDTO.getDateOfDonation()
        );
    }

    public String getNextDonationId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Donation_id from Blood_donation order by Donation_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last Donation ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("DN%03d", newIdIndex); // Return the new Donation ID in format DNnnn
        }
        return "DN001"; // Return the default customer ID if no data is found
    }
}
