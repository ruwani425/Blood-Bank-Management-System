package lk.ijse.gdse.bbms.model;

import lk.ijse.gdse.bbms.dto.BloodTestDTO;
import lk.ijse.gdse.bbms.dto.DonorDTO;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BloodTestModel {
    public String getNextBloodTesdtID() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Test_id from Blood_test order by Test_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last blood test ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("T%03d", newIdIndex); // Return the new test ID in format Tnnn
        }
        return "T001"; // Return the default test ID if no data is found
    }

    public ArrayList<BloodTestDTO> getAllBloodTests(String result) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Blood_test where Test_result=?",result);

        ArrayList<BloodTestDTO> bloodTestDTOS = new ArrayList<>();

        while (rst.next()) {
            BloodTestDTO bloodTestDTO = new BloodTestDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getDate(4),
                    rst.getString(5),
                    rst.getDouble(6),
                    rst.getDate(7),
                    rst.getString(8),
                    rst.getDouble(9),
                    rst.getDouble(10),
                    rst.getDouble(11),
                    rst.getString(12),
                    rst.getString(13)
                    );
            bloodTestDTOS.add(bloodTestDTO);
        }
        return bloodTestDTOS;
    }
    public boolean addBloodTest(BloodTestDTO bloodTestDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into Blood_test values (?,?,?,?,?,?,?,?,?,?,?,?,?)",
                bloodTestDTO.getTestID(),
                bloodTestDTO.getDonationID(),
                bloodTestDTO.getCollectedDate(),
                bloodTestDTO.getExpiryDate(),
                bloodTestDTO.getTestResult(),
                bloodTestDTO.getHaemoglobin(),
                bloodTestDTO.getTestDate(),
                bloodTestDTO.getReportSerialNum(),
                bloodTestDTO.getPlatelets(),
                bloodTestDTO.getRedBloodCells(),
                bloodTestDTO.getWhiteBloodCells(),
                bloodTestDTO.getReportImageUrl(),
                bloodTestDTO.getBloodType()
        );
    }
    public boolean updateBloodTest(BloodTestDTO bloodTestDTO) throws SQLException {
        return CrudUtil.execute(
                "UPDATE Blood_test SET donation_id = ?, Collected_date = ?, Expiry_date = ?, Test_result = ?, Haemoglobin = ?, " +
                        "Test_date = ?, Report_serial_Number = ?, Platelets = ?, Red_blood_cells = ?, White_blood_cells = ?, " +
                        "Report_image_URL = ?,Blood_group=? WHERE Test_id = ?",
                bloodTestDTO.getDonationID(),
                bloodTestDTO.getCollectedDate(),
                bloodTestDTO.getExpiryDate(),
                bloodTestDTO.getTestResult(),
                bloodTestDTO.getHaemoglobin(),
                bloodTestDTO.getTestDate(),
                bloodTestDTO.getReportSerialNum(),
                bloodTestDTO.getPlatelets(),
                bloodTestDTO.getRedBloodCells(),
                bloodTestDTO.getWhiteBloodCells(),
                bloodTestDTO.getReportImageUrl(),
                bloodTestDTO.getTestID(),
                bloodTestDTO.getBloodType()
        );
    }
    public boolean setStatus(String status, String testID) throws SQLException {
        return CrudUtil.execute(
                "UPDATE Blood_test SET Test_result = ? WHERE Test_id = ?",
                status,
                testID
        );
    }
}
