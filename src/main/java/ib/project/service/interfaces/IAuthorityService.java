package ib.project.service.interfaces;

import ib.project.model.Authority;

public interface IAuthorityService {
    Authority findOne(Long id);
    Authority findByName(String name);
    Authority save(Authority authority);
}
