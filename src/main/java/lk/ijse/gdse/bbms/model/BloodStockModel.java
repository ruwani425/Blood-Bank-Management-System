package lk.ijse.gdse.bbms.model;

import lk.ijse.gdse.bbms.dto.BloodStockDTO;
import lk.ijse.gdse.bbms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BloodStockModel {
    public String getNextBloodId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Blood_id from Blood_stock order by Blood_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last blood ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("B%03d", newIdIndex); // Return the new Blood ID in format Bnnn
        }
        return "B001"; // Return the default Blood ID if no data is found
    }
    public ArrayList<BloodStockDTO> getAllBloodStocks(String status) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Blood_stock WHERE status = ?", status);

        ArrayList<BloodStockDTO> bloodStockDTOS = new ArrayList<>();

        while (rst.next()) {
            BloodStockDTO bloodStockDTO = new BloodStockDTO(
                    rst.getString(1),   // bloodID
                    rst.getString(2),   // testID
                    rst.getString(3),   // bloodGroup
                    rst.getInt(4),      // qty
                    rst.getDouble(5),   // haemoglobin
                    rst.getFloat(6),    // platelets
                    rst.getDouble(7),   // redBloodCells
                    rst.getDouble(8),   // whiteBloodCells
                    rst.getDate(9),     // expiryDate
                    rst.getString(10)   // status
            );
            bloodStockDTOS.add(bloodStockDTO);
        }
        return bloodStockDTOS;
    }
    public boolean addBloodStock(BloodStockDTO bloodStockDTO) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO Blood_stock VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                bloodStockDTO.getBloodID(),
                bloodStockDTO.getTestID(),
                bloodStockDTO.getBloodGroup(),
                bloodStockDTO.getQty(),
                bloodStockDTO.getHaemoglobin(),
                bloodStockDTO.getPlatelets(),
                bloodStockDTO.getRedBloodCells(),
                bloodStockDTO.getWhiteBloodCells(),
                bloodStockDTO.getExpiryDate(),
                bloodStockDTO.getStatus()
        );
    }
    public boolean setStatus(String status, String bloodId) throws SQLException {
        return CrudUtil.execute(
                "UPDATE Blood_stock SET status = ? WHERE Blood_id = ?",
                status,
                bloodId
        );
    }
}
