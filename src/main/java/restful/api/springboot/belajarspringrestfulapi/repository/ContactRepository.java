package restful.api.springboot.belajarspringrestfulapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restful.api.springboot.belajarspringrestfulapi.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String> {

}
