package dev.SpringBootAPI.ECommerce.controllers;

import dev.SpringBootAPI.ECommerce.dtos.UserDTO;
import dev.SpringBootAPI.ECommerce.models.user.User;
import dev.SpringBootAPI.ECommerce.services.UserService;
import dev.SpringBootAPI.ECommerce.mappers.UserMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    //Create
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody User user) {
        return ResponseEntity.status(201).body(
                userMapper.toDto(userService.createUser(user))
        );
    }
    //

    //Read
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<User> users = userService.getUsers();

        if (users.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(
                // Realiza o streaming da lista de usuários, mapeando cada usuário para um UserDTO usando o userMapper
                users.stream()
                        .map(userMapper::toDto)  // Converte cada User para UserDTO utilizando o userMapper
                        .collect(Collectors.toList())  // Coleta os resultados em uma lista de UserDTO
        );
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);

        if (user.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(userMapper.toDto(user.get()));
    }
    //

    //Update
    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO updatedUserDTO) {
        Optional<User> existingUser = userService.getUserById(updatedUserDTO.getId());

        if (existingUser.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(userMapper.toDto(userService.updateUser(
                existingUser.get(),
                userMapper.toEntity(updatedUserDTO)))
        );
    }

    @PatchMapping("/{email}")
    public ResponseEntity<UserDTO> patchUserByEmail(@PathVariable String email, @Valid @RequestBody UserDTO partialUpdatedUserDTO) {
        Optional<User> existingUser = userService.getUserByEmail(email);

        if (existingUser.isEmpty())
            return ResponseEntity.notFound().build();

        User partialUpdatedUser = userMapper.toEntity(partialUpdatedUserDTO);
        return ResponseEntity.ok(userMapper.toDto(userService.updateUser(existingUser.get(), partialUpdatedUser)));
    }
    //

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable Long id) {
        Optional<User> userToDelete = userService.getUserById(id);

        if (userToDelete.isEmpty())
            return ResponseEntity.notFound().build();

        userService.deleteUserById(userToDelete.get().getId());
        return ResponseEntity.noContent().build();
    }
    //
}
