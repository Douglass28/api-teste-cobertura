package com.br.dsevoluction.apicoberturateste.services;

import com.br.dsevoluction.apicoberturateste.entities.User;
import com.br.dsevoluction.apicoberturateste.entities.dtos.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User findById (Integer id);
    List<User> findAll();

    User insertUser(UserDto obj);

    Optional<User> updateUser(Integer id, UserDto userDto);
}
