package com.br.dsevoluction.apicoberturateste.services.impl;

import com.br.dsevoluction.apicoberturateste.entities.User;
import com.br.dsevoluction.apicoberturateste.entities.dtos.UserDto;
import com.br.dsevoluction.apicoberturateste.repositories.UserRepository;
import com.br.dsevoluction.apicoberturateste.services.exceptions.DataIntegratyViolationException;
import com.br.dsevoluction.apicoberturateste.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    public static final Integer ID = 1;
    public static final String NAME = "douglas";
    public static final String EMAIL = "douglas@gmail.com";
    public static final String PASSWORD = "123";
    public static final int INDEX = 0;
    public static final String EMAIL_JA_EXISTE_NO_SISTEMA = "email já existe no sistema";
    public static final String USUARIO_NAO_ENCONTRADO = "usuario nao encontrado";
    @InjectMocks
    private UserServiceImpl service;
    @Mock
    private UserRepository repository;
    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDto userDto;
    private Optional<User> optionalUser;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() {
       when(repository.findById(anyInt())).thenReturn(optionalUser);

        User response = service.findById(ID);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());

    }

    @Test
    void whenFindByIdThenReturnObjectNotFoudException(){
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("usuario nao encontrado"));

        try {
            service.findById(ID);
        }catch (ObjectNotFoundException ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("usuario nao encontrado", ex.getMessage());
        }
    }

    @Test
    void WhenFindAllThenReturnAnListOfUsers() {
        when(repository.findAll()).thenReturn(List.of(user));

        List<User> response = repository.findAll();

        assertNotNull(response);
        assertEquals(User.class, response.get(INDEX).getClass());

        assertEquals(1, response.size());
        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(NAME, response.get(INDEX).getName());
        assertEquals(EMAIL, response.get(INDEX).getEmail());
    }

    @Test
    void WhenCreateThenReturnSucess() {
        //cenario de sucesso
        when(repository.save(any())).thenReturn(user);

        User response = service.insertUser(userDto);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());

    }
    @Test
    void WhenCreateThenReturnAnDataIntegratyViolationException(){
        //cenario de falha
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try {
            optionalUser.get().setId(2);
            service.insertUser(userDto);
        } catch (Exception ex){
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals(EMAIL_JA_EXISTE_NO_SISTEMA, ex.getMessage());
        }
    }

    @Test
    void WhenUpdateThenReturnSucess() {
        //cenario de sucesso
        when(repository.save(any())).thenReturn(user);

        User response = service.updateUser(userDto);

        assertNotNull(response);

        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());

    }

    @Test
    void WhenUpdateThenReturnAnDataIntegratyViolationException(){
        //cenario de falha
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try {
            optionalUser.get().setId(2);
            service.insertUser(userDto);
        } catch (Exception ex){
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals(EMAIL_JA_EXISTE_NO_SISTEMA, ex.getMessage());
        }
    }
    @Test
    void deleteWithSuccess() {
        // delete com sucesso
        when(repository.findById(anyInt())).thenReturn(optionalUser);
        doNothing().when(repository).deleteById(anyInt());

        service.delete(ID);
        verify(repository, times(1)).deleteById(anyInt());

    }

    @Test
    void deleteWithObjectNotFoudException(){
        when(repository.findById(anyInt())).thenThrow(ObjectNotFoundException.class);

        try {
            repository.deleteById(ID);
        }catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(USUARIO_NAO_ENCONTRADO, ex.getMessage());
        }
    }

    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDto = new UserDto(ID, NAME, PASSWORD, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}

