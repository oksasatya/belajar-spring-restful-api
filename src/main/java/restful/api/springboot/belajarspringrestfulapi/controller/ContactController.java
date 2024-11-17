package restful.api.springboot.belajarspringrestfulapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import restful.api.springboot.belajarspringrestfulapi.entity.User;
import restful.api.springboot.belajarspringrestfulapi.model.ContactResponse;
import restful.api.springboot.belajarspringrestfulapi.model.CreateContactRequest;
import restful.api.springboot.belajarspringrestfulapi.model.WebResponse;
import restful.api.springboot.belajarspringrestfulapi.service.ContactService;

@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping(
            path = "/api/contacts",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<ContactResponse> create(User user, @RequestBody CreateContactRequest request) {
        contactService.create(user, request);
        return WebResponse.<ContactResponse>builder().data(new ContactResponse()).build();
    }

}
