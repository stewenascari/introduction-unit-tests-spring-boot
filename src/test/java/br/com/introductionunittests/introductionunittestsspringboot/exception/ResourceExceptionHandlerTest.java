package br.com.introductionunittests.introductionunittestsspringboot.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class ResourceExceptionHandlerTest {

    public static final String USER_NOT_FOUND = "user not found!";
    public static final String EMAIL_ALREADY_EXISTS_PLEASE_TRY_AGAIN = "email already exists. Please try again!";
    @InjectMocks
    private ResourceExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Deve retornar um Response Entity quando o usuario buscado nao for encontrado")
    void whenObjectNotFoundExceptionTheReturnAResponseEntity() {
        ResponseEntity<StandarError> response = exceptionHandler
                .objectNotFound(new ObjectNotFoundException(USER_NOT_FOUND), new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandarError.class, response.getBody().getClass());
        assertEquals(USER_NOT_FOUND, response.getBody().getError());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getBody().getStatus());
    }

    @Test
    void whenDataIntegrityViolation() {
        ResponseEntity<StandarError> response = exceptionHandler
                .dataIntegrityViolation(new DataIntegratyViolationException(EMAIL_ALREADY_EXISTS_PLEASE_TRY_AGAIN),
                        new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandarError.class, response.getBody().getClass());
        assertEquals(EMAIL_ALREADY_EXISTS_PLEASE_TRY_AGAIN, response.getBody().getError());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
    }
}