package ib.project.model.dto;

import ib.project.model.Authority;

import java.io.Serializable;

public class AuthorityDto implements Serializable {

    private Long id;

    private String name;

    public AuthorityDto() {}

    public AuthorityDto(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public AuthorityDto(Authority authority) {
        this(authority.getId(), authority.getName());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
