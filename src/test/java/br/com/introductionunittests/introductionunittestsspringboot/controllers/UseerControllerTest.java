package br.com.introductionunittests.introductionunittestsspringboot.controllers;

import br.com.introductionunittests.introductionunittestsspringboot.models.UseerDTO;
import br.com.introductionunittests.introductionunittestsspringboot.models.UseerRequestDTO;
import br.com.introductionunittests.introductionunittestsspringboot.services.impl.UseerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UseerControllerTest {

    public static final Integer ID = 1;
    public static final String NAME = "teste 01";
    public static final String EMAIL = "teste01@gmail.com";
    public static final String PASSWORD = "123";
    public static final int INDEX = 0;

    @InjectMocks
    private UseerController controller;

    @Mock
    private UseerServiceImpl service;

    private UseerRequestDTO requestDTO;
    private UseerDTO dto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestDTO = new UseerRequestDTO(NAME, EMAIL, PASSWORD);
        dto  = new UseerDTO(ID, NAME, EMAIL, PASSWORD);
    }

    @Test
    @DisplayName("Deve retornar o usuario que foi encontrado atraves do id")
    void whenFindbyIdThenReturnSuccess() {
        when(service.findById(anyInt())).thenReturn(dto);

        ResponseEntity<UseerDTO> response = controller.findbyId(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UseerDTO.class, response.getBody().getClass());
        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());
    }

    @Test
    @DisplayName("Deve retornar uma lista de todos os usuarios existente na base")
    void whenFindAllThenReturnSuccess() {
        when(service.findAll()).thenReturn(List.of(dto));

        ResponseEntity<List<UseerDTO>> response = controller.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals(0, response.getBody().size());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UseerDTO.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, response.getBody().get(INDEX).getId());
    }

    @Test
    @DisplayName("Deve remover o usuario que foi solicitado")
    void whenDeleteThenReturnSuccess() {

        service.delete(anyInt());
        ResponseEntity<Void> response = controller.delete(ID);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertNull(response.getBody());

    }

    @Test
    @DisplayName("Deve atualizar os dados do usuario que solicitado")
    void update() {
        service.update(anyInt(), any());

        ResponseEntity<Void> response = controller.update(ID, requestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Deve retornar o usuario que foi criado e o status do response como CREATED")
    void whenCreateThenReturnCreated() {
        when(service.create(any())).thenReturn(dto);

        ResponseEntity<UseerDTO> response = controller.create(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UseerDTO.class, response.getBody().getClass());
    }
}