package com.br.dsevoluction.apicoberturateste.services.impl;

import com.br.dsevoluction.apicoberturateste.entities.User;
import com.br.dsevoluction.apicoberturateste.entities.dtos.UserDto;
import com.br.dsevoluction.apicoberturateste.repositories.UserRepository;
import com.br.dsevoluction.apicoberturateste.services.UserService;
import com.br.dsevoluction.apicoberturateste.services.exceptions.DataIntegratyViolationException;
import com.br.dsevoluction.apicoberturateste.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public User findById(Integer id) {
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(()-> new ObjectNotFoundException("usuario nao encontrado"));
    }

    @Override
    public List<User> findAll() {
        List<User> users = repository.findAll();
        return users;
    }

    @Override
    public User insertUser(UserDto obj) {
        findByEmail(obj);
        return repository.save(mapper.map(obj, User.class));
    }

    @Override
    public Optional<User> updateUser(Integer id, UserDto userDto) {
        findByEmail(userDto);
        User obj = repository.findById(id).get();
        update(obj, userDto);
        repository.save(obj);
        return Optional.of(obj);
    }

    private void update(User user, UserDto obj){
        user.setEmail(obj.getEmail());
        user.setName(obj.getName());
        user.setPassword(obj.getPassword());
    }

    private void findByEmail(UserDto obj){
        Optional<User> user = repository.findByEmail(obj.getEmail());
        if (user.isPresent() && !user.get().getId().equals(obj.getId())) {
            throw new DataIntegratyViolationException("email já existe no sistema");
        }
    }


}
