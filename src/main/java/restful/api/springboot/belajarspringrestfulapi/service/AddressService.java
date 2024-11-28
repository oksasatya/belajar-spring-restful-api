package restful.api.springboot.belajarspringrestfulapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import restful.api.springboot.belajarspringrestfulapi.entity.Address;
import restful.api.springboot.belajarspringrestfulapi.entity.Contact;
import restful.api.springboot.belajarspringrestfulapi.entity.User;
import restful.api.springboot.belajarspringrestfulapi.model.AddressResponse;
import restful.api.springboot.belajarspringrestfulapi.model.CreateAddressRequest;
import restful.api.springboot.belajarspringrestfulapi.repository.AddressRepository;
import restful.api.springboot.belajarspringrestfulapi.repository.ContactRepository;

import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    public ContactRepository contactRepository;

    @Autowired
    public AddressRepository addressRepository;

    @Autowired
    private ValidationService validationService;


    @Transactional
    public AddressResponse create(User user, CreateAddressRequest request){
        validationService.validate(request);

        Contact contact = contactRepository.findFirstByUserAndId(user,request.getContactId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Contact not found"));

        Address address = new Address();
        address.setId(UUID.randomUUID().toString());
        address.setContact(contact);
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setProvince(request.getProvince());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());

        addressRepository.save(address);

        return toAddressResponse(address);
    }

    @Transactional
    public AddressResponse get(User user, String contactId,String addressId){
        Contact contact = contactRepository.findFirstByUserAndId(user,contactId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Contact Is Not Found"));

        Address address = addressRepository.findFirstByContactAndId(contact,addressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Address Is Not Found"));

        return toAddressResponse(address);
    }


    private AddressResponse toAddressResponse(Address address) {
        return AddressResponse.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .province(address.getProvince())
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .build();
    }
}
