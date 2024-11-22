package lk.ijse.gdse.bbms.model;

import lk.ijse.gdse.bbms.dto.BloodRequestDTO;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BloodRequestModel {
    public String getNextRequestId() throws SQLException {
        // Query the database to get the latest Request_id from the BloodRequest table
        ResultSet rst = CrudUtil.execute("select Request_id from Blood_request order by Request_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Get the last Request ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the numeric part by 1
            return String.format("R%03d", newIdIndex); // Return the new Request ID in format Rnnn
        }
        return "R001"; // Return the default Request ID if no data is found
    }
    public boolean addBloodRequest(BloodRequestDTO bloodRequestDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into Blood_request values (?, ?, ?, ?, ?, ?)",
                bloodRequestDTO.getRequestId(),
                bloodRequestDTO.getHospitalId(),
                bloodRequestDTO.getBloodType(),
                bloodRequestDTO.getDateOfRequest(),
                bloodRequestDTO.getQty(),
                bloodRequestDTO.getStatus()
        );
    }

    public ArrayList<BloodRequestDTO> getAllRequests(String status) throws SQLException {
        // Define a query to select all columns from the Blood_request table
        ResultSet rst = CrudUtil.execute("SELECT * FROM Blood_request where Status=?",status);

        // Create an ArrayList to store the results
        ArrayList<BloodRequestDTO> bloodRequestList = new ArrayList<>();

        // Loop through the result set and populate the list
        while (rst.next()) {
            BloodRequestDTO bloodRequest = new BloodRequestDTO(
                    rst.getString("Request_id"),       // Request ID
                    rst.getString("Hospital_id"),      // Hospital ID
                    rst.getString("Blood_group"),       // Blood Type
                    rst.getDate("Date_of_request"),    // Date of Request
                    rst.getDouble("Qty"),              // Quantity
                    rst.getString("Status")            // Status
            );

            // Add the DTO object to the list
            bloodRequestList.add(bloodRequest);
        }

        // Return the populated list
        return bloodRequestList;
    }
    public boolean updateStatus(String requestId) throws SQLException {
        return CrudUtil.execute("UPDATE Blood_request SET Status='COMPLETED' WHERE Request_id=?",requestId);
    }

    public int getTotalRequestBloodCount() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT COUNT(*) FROM Blood_request");

        if (rst.next()) {
            return rst.getInt(1); // Return the total count
        }
        return 0; // Return 0 if no data is found
    }
}
