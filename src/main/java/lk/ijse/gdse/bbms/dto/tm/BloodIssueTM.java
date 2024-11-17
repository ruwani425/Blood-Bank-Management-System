package lk.ijse.gdse.bbms.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodIssueTM {
    private String bloodIssueID;
    private String bloodGroup;
    private double bloodQty;
    private Date expiry;
}
