package io.bootify.bootify_premium_contact_service.user;

import java.util.List;


public interface UserService {

    List<UserDTO> findAll();

    UserDTO get(Long id);

    Long create(UserDTO userDTO);

    void update(Long id, UserDTO userDTO);

    void delete(Long id);

    boolean usernameExists(String username);

    boolean passwordExists(String password);

}
