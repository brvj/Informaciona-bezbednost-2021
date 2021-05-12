package ib.project.service.interfaces;

import ib.project.model.User;

import java.util.List;

public interface IUserService {
    User findOne(Long id);
    User findByEmail(String email);
    List<User> findByActive(boolean active);
    List<User> searchByEmail(String email);
    User save(User save);
}
