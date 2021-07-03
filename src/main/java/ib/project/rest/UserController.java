package ib.project.rest;

import ib.project.certificate.CertificateGenerator;
import ib.project.certificate.CertificateReader;
import ib.project.model.User;
import ib.project.model.dto.UserDto;
import ib.project.service.AuthorityService;
import ib.project.service.UserService;
import ib.project.util.Base64;
import ib.project.util.PasswordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto userDto){
        if(userService.findByEmail(userDto.getEmail()) != null)
            return new ResponseEntity<UserDto>(HttpStatus.CONFLICT);

        User user = new User();
        user.setEmail(userDto.getEmail());
        String password = userDto.getPassword();
        byte[] salt = PasswordManager.generateSalt();
        user.setSalt(Base64.encodeToString(salt));
        byte[] hashedPass = PasswordManager.hashPassword(password, salt);
        userDto.setPassword(Base64.encodeToString(hashedPass));
        user.setActive(false);
        user.setAuthority(authorityService.findByName("regular"));

        user = userService.save(user);

        CertificateGenerator.generateCertificate(user);
        userDto.setCertificate("./data" + user.getId() + ".cer");

        user = userService.save(user);
        return new ResponseEntity<UserDto>(new UserDto(user), HttpStatus.CREATED);
    }

    @GetMapping(path = "/inactive")
    public ResponseEntity<List<UserDto>> getInactiveUsers(){
        List<User> inactiveUsers = userService.findByActive(false);
        List<UserDto> users = new ArrayList<UserDto>();

        for(User user: inactiveUsers){
            users.add(new UserDto(user));
        }
        return new ResponseEntity<List<UserDto>>(users, HttpStatus.OK);
    }

    @PutMapping(value = "/activation/{id}")
    public ResponseEntity<UserDto> activation(@PathVariable("id") Long id){
        User user = userService.findOne(id);
        if(user == null) return new ResponseEntity<UserDto>(HttpStatus.BAD_REQUEST);

        user.setActive(true);

        user = userService.save(user);
        return new ResponseEntity<UserDto>(new UserDto(user), HttpStatus.OK);
    }

    @GetMapping(path = "/searchByEmail/{email}")
    public ResponseEntity<List<UserDto>> searchUsersByEmail(@PathVariable("email") String email) {
        if (email == null) email = "";
        List<User> users = userService.searchByEmail(email);
        List<UserDto> usersDTO = new ArrayList<UserDto>();

        for (User user : users) {
            usersDTO.add(new UserDto(user));
        }

        return new ResponseEntity<List<UserDto>>(usersDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/newCertificate/{id}")
    public ResponseEntity<Void> createNewCertificate(@PathVariable("id") Long id) {
        User user = userService.findOne(id);
        if (user == null) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

        CertificateGenerator.generateCertificate(user);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(path = "/downloadCertificate/{id}")
    public ResponseEntity<byte[]> downloadCertificate(@PathVariable("id") Long id) {
        Certificate certificate = CertificateReader.readBase64EncodedCertificate
                ("./data/" + id + ".cer");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("filename", id + ".cer");

        byte[] bFile = new byte[0];
        try {
            bFile = certificate.getEncoded();
            return ResponseEntity.ok().headers(headers).body(bFile);
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().headers(headers).body(bFile);
    }

    @GetMapping(path = "/downloadKeyStore/{email}")
    public ResponseEntity<byte[]> downloadKeyStore(@PathVariable("email") String email){
        User user = userService.findByEmail(email);
        if(user == null) return new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("filename", user.getId() + ".jks");

        byte[] bFile = DemoController.readBytesFromFile("./data" + user.getId() + ".jks");

        return ResponseEntity.ok().headers(headers).body(bFile);
    }
}
