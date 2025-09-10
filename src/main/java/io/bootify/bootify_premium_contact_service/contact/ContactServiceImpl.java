package io.bootify.bootify_premium_contact_service.contact;

import io.bootify.bootify_premium_contact_service.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public ContactServiceImpl(final ContactRepository contactRepository,
            final ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    @Override
    public Page<ContactDTO> findAll(final String filter, final Pageable pageable) {
        Page<Contact> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = contactRepository.findAllById(longFilter, pageable);
        } else {
            page = contactRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(contact -> contactMapper.updateContactDTO(contact, new ContactDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    @Override
    public ContactDTO get(final Long id) {
        return contactRepository.findById(id)
                .map(contact -> contactMapper.updateContactDTO(contact, new ContactDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final ContactDTO contactDTO) {
        final Contact contact = new Contact();
        contactMapper.updateContact(contactDTO, contact);
        return contactRepository.save(contact).getId();
    }

    @Override
    public void update(final Long id, final ContactDTO contactDTO) {
        final Contact contact = contactRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        contactMapper.updateContact(contactDTO, contact);
        contactRepository.save(contact);
    }

    @Override
    public void delete(final Long id) {
        final Contact contact = contactRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        contactRepository.delete(contact);
    }

    @Override
    public boolean phoneNumberExists(final String phoneNumber) {
        return contactRepository.existsByPhoneNumberIgnoreCase(phoneNumber);
    }

}
