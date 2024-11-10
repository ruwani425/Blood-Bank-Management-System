package lk.ijse.gdse.bbms.model;
import lk.ijse.gdse.bbms.dto.DonorDTO;
import lk.ijse.gdse.bbms.util.CrudUtil;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DonorModel{
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

    public ArrayList<DonorDTO> getAllDonors() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from Donor");

        ArrayList<DonorDTO> donorDTOS = new ArrayList<>();

        while (rst.next()) {
            DonorDTO donorDTO = new DonorDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getDate(8),
                    rst.getDate(9)
            );
            donorDTOS.add(donorDTO);
        }
        return donorDTOS;
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

    public boolean deleteDonor(String donorId) throws SQLException {
        return CrudUtil.execute("delete from Donor where Donor_id=?", donorId);
    }

    public boolean updateDonor(DonorDTO donorDTO) throws SQLException {
        return CrudUtil.execute(
            "update Donor set Name=?, Donor_nic=?, Address=?, E_mail=?,Blood_group=?,Gender=?,Dob=?,Last_donation_date=? where Donor_id=?",
            donorDTO.getDonorName(),
            donorDTO.getDonorNic(),
            donorDTO.getDonorAddress(),
            donorDTO.getDonorEmail(),
            donorDTO.getBloodGroup(),
            donorDTO.getGender(),
            donorDTO.getDob(),
            donorDTO.getLastDonationDate(),
            donorDTO.getDonorId()
            );
    }
}
