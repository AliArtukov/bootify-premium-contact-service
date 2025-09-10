package io.bootify.bootify_premium_contact_service.user;

import io.bootify.bootify_premium_contact_service.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(final UserRepository userRepository,
            final PasswordEncoder passwordEncoder, final UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> userMapper.updateUserDTO(user, new UserDTO()))
                .toList();
    }

    @Override
    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> userMapper.updateUserDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final UserDTO userDTO) {
        final User user = new User();
        userMapper.updateUser(userDTO, user, passwordEncoder);
        return userRepository.save(user).getId();
    }

    @Override
    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        userMapper.updateUser(userDTO, user, passwordEncoder);
        userRepository.save(user);
    }

    @Override
    public void delete(final Long id) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        userRepository.delete(user);
    }

    @Override
    public boolean usernameExists(final String username) {
        return userRepository.existsByUsernameIgnoreCase(username);
    }

    @Override
    public boolean passwordExists(final String password) {
        return userRepository.existsByPasswordIgnoreCase(password);
    }

}
