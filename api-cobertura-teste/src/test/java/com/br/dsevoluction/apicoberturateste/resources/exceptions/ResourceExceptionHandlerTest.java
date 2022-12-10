package com.br.dsevoluction.apicoberturateste.resources.exceptions;

import com.br.dsevoluction.apicoberturateste.services.exceptions.DataIntegratyViolationException;
import com.br.dsevoluction.apicoberturateste.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ResourceExceptionHandlerTest {

    private static final String OBJTEO_NAO_ENCONTRADO = "objeto nao encontrado";
    private static final String EMAIL_JA_EXISTE = "Email ja existe";

    @InjectMocks
    private ResourceExceptionHandler exceptionHandler;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenObjectNotFoundExceptionThenReturnAResponseEntity() {
        ResponseEntity<StandardError> response = exceptionHandler.objctNotFound(
                new ObjectNotFoundException(OBJTEO_NAO_ENCONTRADO), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(OBJTEO_NAO_ENCONTRADO, response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());

    }

    @Test
    void dataIntegratyViolationException() {

        ResponseEntity<StandardError> response = exceptionHandler.objctNotFound(
                new DataIntegratyViolationException(EMAIL_JA_EXISTE), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(EMAIL_JA_EXISTE, response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }

    @Test
    void testObjctNotFound1() {
    }
}