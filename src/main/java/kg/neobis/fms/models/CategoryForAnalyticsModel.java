package kg.neobis.fms.models;

import lombok.Data;

@Data
public class CategoryForAnalyticsModel {
    private Long id;// category id
    private String name;// category name
    private Double amount;
}
