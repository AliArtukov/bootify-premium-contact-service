package io.bootify.bootify_premium_contact_service.contact;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ContactMapper {

    ContactDTO updateContactDTO(Contact contact, @MappingTarget ContactDTO contactDTO);

    @Mapping(target = "id", ignore = true)
    Contact updateContact(ContactDTO contactDTO, @MappingTarget Contact contact);

}
