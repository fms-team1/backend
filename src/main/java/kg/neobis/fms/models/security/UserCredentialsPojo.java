package kg.neobis.fms.models.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCredentialsPojo {
    private String oldPassword;
    private String newPassword;
    private String newPassword1;
}
