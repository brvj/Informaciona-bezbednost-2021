package ib.project.model.dto;

import ib.project.model.User;

import java.io.Serializable;

public class UserDto implements Serializable {

    private Long id;

    private String email;

    private String password;

    private String salt;

    private String certificate;

    private boolean active;

    private AuthorityDto authority;

    public UserDto() {}

    public UserDto(Long id, String email, String password,String salt, String certificate, boolean active, AuthorityDto authority) {
        super();
        this.id = id;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.certificate = certificate;
        this.active = active;
        this.authority = authority;
    }

    public UserDto(User user) {
        this(user.getId(), user.getEmail(), user.getPassword(), user.getSalt(), user.getCertificate(), user.isActive(),
                new AuthorityDto(user.getAuthority()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {return salt;}

    public void setSalt(String salt) {this.salt = salt;}

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public AuthorityDto getAuthority() {
        return authority;
    }

    public void setAuthority(AuthorityDto authority) {
        this.authority = authority;
    }
}
