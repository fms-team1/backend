package kg.neobis.fms.models;

import lombok.Data;

@Data
public class ModelToUpdateProfileWithPassword {
    private String surname;
    private String name;
    private String phoneNumber;
    private String newPassword;
    private String oldPassword;
}
