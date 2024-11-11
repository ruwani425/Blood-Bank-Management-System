package lk.ijse.gdse.bbms.model;

import lk.ijse.gdse.bbms.dto.HealthCheckupDTO;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HealthCheckUpModel {
    public boolean addHealthCheckup(HealthCheckupDTO healthCheckupDTO) {
        System.out.println("success");
        return false;
    }

    public String getNextHealthCheckUpId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Checkup_id from Health_checkup order by Checkup_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last healthCheck ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("H%03d", newIdIndex); // Return the new Donor ID in format Hnnn
        }
        return "H001"; // Return the default HealthCheck ID if no data is found
    }
}
