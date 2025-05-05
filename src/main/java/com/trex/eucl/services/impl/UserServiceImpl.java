package com.trex.eucl.services.impl;

import com.trex.eucl.entities.User;
import com.trex.eucl.exceptions.BadRequestException;
import com.trex.eucl.repository.UserRepository;
import com.trex.eucl.request.UpdateUserRequest;
import com.trex.eucl.services.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw new BadRequestException(String.format("The user with id %s doesn't exist", id));

        userRepository.deleteById(id);

        return user.get();
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) throw new BadRequestException(String.format("The user with email %s doesn't exist", email));

        return user.get();
    }

    @Override
    public User updateUser(UpdateUserRequest userUpdates, UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new BadRequestException(
                    String.format("The user with id %s doesn't exist", id)
            );
        }

        User existingUser = user.get();

        boolean hasChanges = false;

        if (userUpdates.getEmail() != null && !userUpdates.getEmail().equals(existingUser.getEmail())) {
            existingUser.setEmail(userUpdates.getEmail());
            hasChanges = true;
        }

        if (userUpdates.getNames() != null && !userUpdates.getNames().equals(existingUser.getNames())) {
            existingUser.setNames(userUpdates.getNames());
            hasChanges = true;
        }

        if (userUpdates.getNationalId() != null && !userUpdates.getNationalId().equals(existingUser.getNationalId())) {
            existingUser.setNationalId(userUpdates.getNationalId());
            hasChanges = true;
        }

        if (userUpdates.getPhone() != null && !userUpdates.getPhone().equals(existingUser.getPhone())) {
            existingUser.setPhone(userUpdates.getPhone());
            hasChanges = true;
        }

        if (!hasChanges) {
            return existingUser;
        }

        return userRepository.save(existingUser);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }


    @Override
    public User deleteUser(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) throw new BadRequestException(String.format("The user with id %s doesn't exist", id));

        userRepository.delete(user.get());
        return user.get();
    }
}
