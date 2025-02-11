package restful.api.springboot.belajarspringrestfulapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import restful.api.springboot.belajarspringrestfulapi.entity.User;
import restful.api.springboot.belajarspringrestfulapi.model.AddressResponse;
import restful.api.springboot.belajarspringrestfulapi.model.CreateAddressRequest;
import restful.api.springboot.belajarspringrestfulapi.model.UpdateAddressRequest;
import restful.api.springboot.belajarspringrestfulapi.model.WebResponse;
import restful.api.springboot.belajarspringrestfulapi.service.AddressService;

import java.util.List;

import static org.springframework.http.codec.ServerSentEvent.builder;

@RestController
public class AddressController {

    private static final Logger log = LoggerFactory.getLogger(AddressController.class);
    @Autowired
    private AddressService addressService;
    @Autowired
    private RestClient.Builder builder;

    @PostMapping(
            path = "/api/contacts/{contactId}/addresses",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AddressResponse>create(User user,
                                              @RequestBody CreateAddressRequest request,
                                              @PathVariable("contactId") String contactId) {
        request.setContactId(contactId);
        AddressResponse addressResponse = addressService.create(user, request);
        return WebResponse.<AddressResponse>builder().data(addressResponse).build();
    }


    @GetMapping(
            path = "/api/contacts/{contactId}/addresses/{addressId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AddressResponse> get(User user,
                                            @PathVariable("contactId") String contactId,
                                            @PathVariable("addressId") String addressId) {
        AddressResponse addressResponse = addressService.get(user, contactId, addressId);
        return WebResponse.<AddressResponse>builder().data(addressResponse).build();
    }

    @PutMapping(
            path = "/api/contacts/{contactId}/addresses/{addressId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<AddressResponse>update(User user,
                                              @RequestBody UpdateAddressRequest request,
                                              @PathVariable("contactId") String contactId,
                                              @PathVariable("addressId")String addressId) {
        request.setContactId(contactId);
        request.setAddressId(addressId);
        AddressResponse addressResponse = addressService.update(user, request);
        return WebResponse.<AddressResponse>builder().data(addressResponse).build();
    }

    @DeleteMapping(
            path = "/api/contacts/{contactId}/addresses/{addressId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String>remove(User user,
                                              @PathVariable("contactId") String contactId,
                                              @PathVariable("addressId")String addressId) {
        addressService.remove(user, contactId, addressId);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(
            path = "/api/contacts/{contactId}/addresses",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<AddressResponse>> list(User user,
                                            @PathVariable("contactId") String contactId) {
        List<AddressResponse> responses = addressService.list(user, contactId);
        return WebResponse.<List<AddressResponse>>builder().data(responses).build();
    }
}
