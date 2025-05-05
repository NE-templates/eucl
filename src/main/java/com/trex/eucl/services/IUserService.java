package com.trex.eucl.services;

import com.trex.eucl.entities.User;
import com.trex.eucl.request.UpdateUserRequest;

import java.util.List;
import java.util.UUID;

public interface IUserService {

    User getUserById(UUID id);

    User getUserByEmail(String email);

    User updateUser(UpdateUserRequest userUpdates, UUID id);

    List<User> getAll();

    User deleteUser(UUID id);

}
