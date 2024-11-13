package lk.ijse.gdse.bbms.model;

import lk.ijse.gdse.bbms.db.DBConnection;
import lk.ijse.gdse.bbms.dto.DonationDTO;
import lk.ijse.gdse.bbms.dto.HealthCheckupDTO;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DonationModel {
    public boolean addDonation(HealthCheckupDTO healthCheckupDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            DonationDTO donationDTO = healthCheckupDTO.getDonation();

            // Check if donationDTO is null
            if (donationDTO == null) {
                System.out.println("DonationDTO is null. Cannot proceed with donation.");
                return false; // Or handle it as needed
            }

            boolean isHealthCheckupSaved = CrudUtil.execute("insert into Health_checkup values(?,?,?,?,?,?,?)",
                    healthCheckupDTO.getCheckupId(),
                    healthCheckupDTO.getDonorId(),
                    healthCheckupDTO.getHealthStatus(),
                    healthCheckupDTO.getCheckupDate(),
                    healthCheckupDTO.getWeight(),
                    healthCheckupDTO.getSugarLevel(),
                    healthCheckupDTO.getBloodPressure());

            if (isHealthCheckupSaved) {
                boolean isDonorSaved = CrudUtil.execute("insert into Blood_donation values(?,?,?,?,?,?)",
                        donationDTO.getDonation_id(),
                        donationDTO.getBlood_campaign_id(),
                        donationDTO.getHealth_checkup_id(),
                        donationDTO.getBlood_group(),
                        donationDTO.getQty(),
                        donationDTO.getDateOfDonation());

                if (isDonorSaved) {
                    connection.commit();
                    return true;
                }
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
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