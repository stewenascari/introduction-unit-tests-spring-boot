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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UseerControllerTest {

    public static final Integer ID = 1;
    public static final String NAME = "teste 01";
    public static final String EMAIL = "teste01@gmail.com";
    public static final String PASSWORD = "123";

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
    void findAll() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void create() {
    }
}