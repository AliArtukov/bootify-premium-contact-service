package io.bootify.bootify_premium_contact_service.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    @UserUsernameUnique
    private String username;

    @NotNull
    @Size(max = 255)
    @UserPasswordUnique
    private String password;

    @NotNull
    @Size(max = 255)
    private String roles;

    @Size(max = 255)
    private String mail;

    @Size(max = 255)
    private String firstname;

    @Size(max = 255)
    private String lastname;

    @Size(max = 255)
    private String externalId;

}
