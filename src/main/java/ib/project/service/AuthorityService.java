package ib.project.service;

import ib.project.model.Authority;
import ib.project.repository.AuthorityRepository;
import ib.project.service.interfaces.IAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService implements IAuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Authority findOne(Long id) {
        return authorityRepository.getOne(id);
    }

    @Override
    public Authority findByName(String name) {
        return authorityRepository.findByName(name);
    }

    @Override
    public Authority save(Authority authority) {
        return authorityRepository.save(authority);
    }
}
