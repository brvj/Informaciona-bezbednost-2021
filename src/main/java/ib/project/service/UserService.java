package ib.project.service;

import ib.project.model.User;
import ib.project.repository.UserRepository;
import ib.project.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findOne(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findByActive(boolean active) {
        return userRepository.findByActive(active);
    }

    @Override
    public List<User> searchByEmail(String email) {
        return userRepository.findByEmailIgnoreCaseContaining(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
