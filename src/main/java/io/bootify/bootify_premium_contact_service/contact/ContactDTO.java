package io.bootify.bootify_premium_contact_service.contact;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ContactDTO {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String fullname;

    @Size(max = 13)
    @ContactPhoneNumberUnique
    private String phoneNumber;

}
