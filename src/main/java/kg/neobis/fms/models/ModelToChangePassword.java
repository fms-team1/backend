package kg.neobis.fms.models;

import lombok.Data;

@Data
public class ModelToChangePassword {
    private String oldPassword;
    private String newPassword;
}
