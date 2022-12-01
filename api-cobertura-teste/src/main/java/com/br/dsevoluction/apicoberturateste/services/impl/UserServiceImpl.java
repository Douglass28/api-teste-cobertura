package com.br.dsevoluction.apicoberturateste.services.impl;

import com.br.dsevoluction.apicoberturateste.entities.User;
import com.br.dsevoluction.apicoberturateste.repositories.UserRepository;
import com.br.dsevoluction.apicoberturateste.services.UserService;
import com.br.dsevoluction.apicoberturateste.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User findById(Integer id) {
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(()-> new ObjectNotFoundException("usuario nao encontrado"));
    }
}
