package kg.neobis.fms.models;

import lombok.Data;

import java.util.List;

@Data
public class AnalyticsModel {
    private Double totalBalance;
    private List<CategoryForAnalyticsModel> details;
}