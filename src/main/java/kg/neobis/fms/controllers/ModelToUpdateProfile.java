package kg.neobis.fms.controllers;

import kg.neobis.fms.entity.enums.UserStatus;
import lombok.Data;

import java.util.Set;

@Data
public class ModelToUpdateProfile {
    private String surname;
    private String name;
    private String phoneNumber;
}

