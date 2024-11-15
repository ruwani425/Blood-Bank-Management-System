package lk.ijse.gdse.bbms.model;

import lk.ijse.gdse.bbms.db.DBConnection;
import lk.ijse.gdse.bbms.dto.DonationDTO;
import lk.ijse.gdse.bbms.dto.HealthCheckupDTO;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DonationModel {
    public boolean addDonation(DonationDTO donationDTO) throws SQLException {

//    return CrudUtil.execute("insert into Blood_donation values(?,?,?,?,?,?)","D002","C001","H010","A_POSITIVE",10,"2021-08-20");
        return CrudUtil.execute("insert into Blood_donation values(?,?,?,?,?,?)",
                donationDTO.getDonationId(),
                donationDTO.getCampaignId(),
                donationDTO.getHelthCheckupId(),
                donationDTO.getBloodGroup(),
                donationDTO.getQty(),
                donationDTO.getDateOfDonation());
    }

    public String getNextDonationId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Donation_id from Blood_donation order by Donation_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last Donation ID
            if (lastId != null && lastId.startsWith("DN")) {
                String substring = lastId.substring(2); // Extract the numeric part (skip "DN")
                try {
                    int i = Integer.parseInt(substring); // Convert the numeric part to integer
                    int newIdIndex = i + 1; // Increment the number by 1
                    return String.format("DN%03d", newIdIndex); // Return the new Donation ID in format DNnnn
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing donation ID: " + lastId);
                    // Handle the error appropriately, maybe return a default value
                }
            } else {
                System.out.println("Unexpected ID format: " + lastId);
            }
        }
        return "DN001"; // Return the default donation ID if no valid data is found
    }

}