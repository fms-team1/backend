package kg.neobis.fms.models;

import lombok.Data;

import java.sql.Date;

@Data
public class ModelToGetAnalytics {
    private Integer neoSectionId;
    private Integer transactionTypeId;
    private Date startDate;
    private Date endDate;
}
