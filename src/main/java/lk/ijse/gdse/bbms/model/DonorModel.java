package lk.ijse.gdse.bbms.model;
import lk.ijse.gdse.bbms.dto.DonorDTO;
import lk.ijse.gdse.bbms.util.CrudUtil;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DonorModel implements Serializable {
    public String getNextDonorId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select Donor_id from Donor order by Donor_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("D%03d", newIdIndex); // Return the new Donor ID in format Dnnn
        }
        return "D001"; // Return the default customer ID if no data is found
    }

    public boolean addDonor(DonorDTO donorDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into Donor values (?,?,?,?,?,?,?,?,?)",
                donorDTO.getDonorId(),
                donorDTO.getDonorName(),
                donorDTO.getDonorNic(),
                donorDTO.getDonorAddress(),
                donorDTO.getDonorEmail(),
                donorDTO.getBloodGroup(),
                donorDTO.getGender(),
                donorDTO.getDob(),
                donorDTO.getLastDonationDate()
        );
    }
}
