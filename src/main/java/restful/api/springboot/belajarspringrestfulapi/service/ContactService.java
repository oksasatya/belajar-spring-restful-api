package restful.api.springboot.belajarspringrestfulapi.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restful.api.springboot.belajarspringrestfulapi.entity.Contact;
import restful.api.springboot.belajarspringrestfulapi.entity.User;
import restful.api.springboot.belajarspringrestfulapi.model.ContactResponse;
import restful.api.springboot.belajarspringrestfulapi.model.CreateContactRequest;
import restful.api.springboot.belajarspringrestfulapi.repository.ContactRepository;

import java.util.UUID;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public ContactResponse create(User user,CreateContactRequest request){
        validationService.validate(request);

        Contact contact = new Contact();
        contact.setId(UUID.randomUUID().toString());
        contact.setFirstName(request.getFirstName());
        contact.setLastName(request.getLastName());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());
        contact.setUser(user);
        contactRepository.save(contact);

        return ContactResponse.builder()
                .id(contact.getId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();
    }
}
