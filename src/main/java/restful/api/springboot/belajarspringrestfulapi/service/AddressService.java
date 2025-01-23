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
import restful.api.springboot.belajarspringrestfulapi.model.UpdateAddressRequest;
import restful.api.springboot.belajarspringrestfulapi.repository.AddressRepository;
import restful.api.springboot.belajarspringrestfulapi.repository.ContactRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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


    @Transactional
    public AddressResponse update(User user, UpdateAddressRequest request){
        validationService.validate(request);
        Contact contact = contactRepository.findFirstByUserAndId(user,request.getContactId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Contact Is Not Found"));

        Address address = addressRepository.findFirstByContactAndId(contact, request.getAddressId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Address Is Not Found"));

        return getAddressResponse(address, request.getStreet(), request.getCity(), request.getProvince(), request.getCountry(), request.getPostalCode(), request);
    }

    @Transactional
    public void remove(User user, String contactId, String addressId){
        Contact contact = contactRepository.findFirstByUserAndId(user,contactId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Contact Is Not Found"));

        Address address = addressRepository.findFirstByContactAndId(contact, addressId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Address Is Not Found"));

        addressRepository.delete(address);
    }

    @Transactional(readOnly = true)
    public List<AddressResponse> list(User user,String contactId){
        Contact contact = contactRepository.findFirstByUserAndId(user,contactId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Contact Is Not Found"));

        List<Address> addresses = addressRepository.findAllByContact(contact);
        return addresses.stream().map(this::toAddressResponse).collect(Collectors.toList());
    }

    private AddressResponse getAddressResponse(Address address, String street, String city, String province, String country, String postalCode, UpdateAddressRequest request) {
        address.setStreet(street);
        address.setCity(city);
        address.setProvince(province);
        address.setCountry(country);
        address.setPostalCode(postalCode);
        addressRepository.save(address);
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
