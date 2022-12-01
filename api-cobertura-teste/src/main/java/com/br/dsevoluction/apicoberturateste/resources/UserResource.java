package com.br.dsevoluction.apicoberturateste.resources;

import com.br.dsevoluction.apicoberturateste.entities.dtos.UserDto;
import com.br.dsevoluction.apicoberturateste.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body(mapper.map(service.findById(id), UserDto.class));
    }

    @GetMapping
    private ResponseEntity<List<UserDto>> findAll(){
        return ResponseEntity.ok(service.findAll().stream().map(x -> mapper.map(x, UserDto.class))
                .collect(Collectors.toList()));
    }
}
