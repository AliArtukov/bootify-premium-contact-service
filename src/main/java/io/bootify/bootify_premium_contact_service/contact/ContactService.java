package io.bootify.bootify_premium_contact_service.contact;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ContactService {

    Page<ContactDTO> findAll(String filter, Pageable pageable);

    ContactDTO get(Long id);

    Long create(ContactDTO contactDTO);

    void update(Long id, ContactDTO contactDTO);

    void delete(Long id);

    boolean phoneNumberExists(String phoneNumber);

}
