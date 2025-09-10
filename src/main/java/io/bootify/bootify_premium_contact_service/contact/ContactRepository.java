package io.bootify.bootify_premium_contact_service.contact;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContactRepository extends JpaRepository<Contact, Long> {

    Page<Contact> findAllById(Long id, Pageable pageable);

    boolean existsByPhoneNumberIgnoreCase(String phoneNumber);

}
