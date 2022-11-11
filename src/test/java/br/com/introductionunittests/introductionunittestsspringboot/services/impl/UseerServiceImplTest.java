package br.com.introductionunittests.introductionunittestsspringboot.services.impl;

import br.com.introductionunittests.introductionunittestsspringboot.Repository.UseerRepository;
import br.com.introductionunittests.introductionunittestsspringboot.entities.Useer;
import br.com.introductionunittests.introductionunittestsspringboot.exception.DataIntegratyViolationException;
import br.com.introductionunittests.introductionunittestsspringboot.exception.ObjectNotFoundException;
import br.com.introductionunittests.introductionunittestsspringboot.models.UseerDTO;
import br.com.introductionunittests.introductionunittestsspringboot.models.UseerRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UseerServiceImplTest {

    public static final Integer ID = 1;
    public static final String NAME = "teste 01";
    public static final String EMAIL = "teste01@gmail.com";
    public static final String PASSWORD = "123";
    public static final String EMAIL_ALREADY_EXISTS_PLEASE_TRY_AGAIN = "email already exists. Please try again!";

    @InjectMocks
    private UseerServiceImpl service;

    @Mock
    private UseerRepository repository;

    private Useer user;
    private UseerRequestDTO requestDTO;
    private UseerDTO dto;
    private Optional<Useer> optionalUseer;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new Useer(ID, NAME, EMAIL, PASSWORD);
        requestDTO = new UseerRequestDTO(NAME, EMAIL, PASSWORD);
        optionalUseer = Optional.of(new Useer(ID, NAME, EMAIL, PASSWORD));
        dto  = new UseerDTO(ID, NAME, EMAIL, PASSWORD);
    }

    @Test
    @DisplayName("Deve salvar e retornar o usuario salvo")
    void whenCreateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(user);

        var response = service.create(requestDTO);

        assertNotNull(response);
        assertEquals(UseerDTO.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(EMAIL, response.getEmail());


    }

    @Test
    @DisplayName("Deve retornar um exception quando o usuario possuir um email ja existente no banco")
    void whenCreateThenReturnAnEmailAlreadyExists() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUseer);

        try{
            optionalUseer.get().setId(2);
            service.create(requestDTO);
        }catch (Exception ex){
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals(EMAIL_ALREADY_EXISTS_PLEASE_TRY_AGAIN, ex.getMessage());
        }


    }



    @Test
    @DisplayName("Deve buscar um usuario atraves do id que foi passado na requisicao")
    void whenFindByIdThenReturnAnUserInstance() throws ObjectNotFoundException {
        when(repository.findById(anyInt())).thenReturn(optionalUseer);
        UseerDTO response = service.findById(ID);

        assertNotNull(response);
        assertEquals(UseerDTO.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
    }

    @Test
    @DisplayName("Deve retornar um exception quando o usuario nao for enconrtado")
    void whenFindByIdThenReturnAnObjectNotFoundException(){
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("user not found!"));

        try{
            service.findById(ID);
        }catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("user not found!", ex.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        when(repository.findAll()).thenReturn(List.of(user));

        var users = service.findAll();
        var userResponse = users.get(0);

        assertNotEquals(0, users.size());
        assertEquals(UseerDTO.class, users.get(0).getClass());

        assertEquals(ID, userResponse.getId());
        assertEquals(NAME, userResponse.getName());
        assertEquals(EMAIL, userResponse.getEmail());
        assertEquals(PASSWORD, userResponse.getPassword());
    }

    @Test
    @DisplayName("Deve atualizar e retornar o usuario salvo")
    void whenUpdateThenReturnSuccess() {

        when(repository.findById(ID)).thenReturn(optionalUseer);
        when(repository.save(any())).thenReturn(user);

        service.update(ID, requestDTO);

        verify(repository).save(user);


    }

    @Test
    @DisplayName("Deve retornar um exception quando o usuario possuir um email ja existente no banco")
    void whenUpdateThenReturnAnEmailAlreadyExists() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUseer);

        try{
            optionalUseer.get().setId(2);
            service.create(requestDTO);
        }catch (Exception ex){
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals(EMAIL_ALREADY_EXISTS_PLEASE_TRY_AGAIN, ex.getMessage());
        }


    }

    @Test
    @DisplayName("Deve retornar que o usuario solicitado foi deletado com sucesso")
    void whenDeleteWithSuccess() {
        when(repository.findById(anyInt())).thenReturn(optionalUseer);
        doNothing().when(repository).deleteById(anyInt()); // nao fazer nds quando o metodo findById for chamado pelo delete
        service.delete(ID);
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    @DisplayName("Deve retornar um exception quando o usuario nao for encontrado")
    void whenDeleteWithUserNotFoundException(){
        when(repository.findById(anyInt()))
                .thenThrow(new ObjectNotFoundException("user not found!"));

        try{
            service.delete(ID);

        }catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("user not found!", ex.getMessage());

        }

    }
}