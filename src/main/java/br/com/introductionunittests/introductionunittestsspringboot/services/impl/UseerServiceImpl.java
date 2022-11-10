package br.com.introductionunittests.introductionunittestsspringboot.services.impl;

import br.com.introductionunittests.introductionunittestsspringboot.Repository.UseerRepository;
import br.com.introductionunittests.introductionunittestsspringboot.entities.Useer;
import br.com.introductionunittests.introductionunittestsspringboot.exception.DataIntegratyViolationExceptionException;
import br.com.introductionunittests.introductionunittestsspringboot.exception.ObjectNotFoundException;
import br.com.introductionunittests.introductionunittestsspringboot.models.UseerDTO;
import br.com.introductionunittests.introductionunittestsspringboot.models.UseerRequestDTO;
import br.com.introductionunittests.introductionunittestsspringboot.models.mappers.UserMapper;
import br.com.introductionunittests.introductionunittestsspringboot.services.UseerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UseerServiceImpl implements UseerService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UseerRepository repository;

    @Override
    public UseerDTO create(UseerRequestDTO dto){
        this.checkIfExistEmail(dto.getEmail(), null);
        return UserMapper.INSTANCE.userToUserDTO(repository.save(UserMapper.INSTANCE.userRequestToUser(dto)));
    }

    @Override
    public UseerDTO findById(Integer id) {
        return Optional.ofNullable(repository.findById(id).get())
                .map(user -> UserMapper.INSTANCE.userToUserDTO(user))
                .orElseThrow(() -> new ObjectNotFoundException("user not found! id:"+ String.valueOf(id)));
    }

    @Override
    public Useer findByEntityId(Integer id) {
        return Optional.ofNullable(repository.findById(id).get())
                .orElseThrow(() -> new ObjectNotFoundException("user not found! id:"+ String.valueOf(id)));
    }

    public List<UseerDTO> findAll() {
        return repository.findAll().stream()
                .map(user -> UserMapper.INSTANCE.userToUserDTO(user))
                .collect(Collectors.toList());
    }

    public void update(Integer id, UseerRequestDTO dto){

        var entity = this.findByEntityId(id);
        this.checkIfExistEmail(dto.getEmail(), id);

        entity.setName(Optional.ofNullable(dto.getName()).orElse(entity.getName()));
        entity.setEmail(Optional.ofNullable(dto.getEmail()).orElse(entity.getEmail()));
        entity.setPassword(Optional.ofNullable(dto.getPassword()).orElse(entity.getPassword()));

        Optional.ofNullable(repository.save(entity))
                .map(user -> UserMapper.INSTANCE.userToUserDTO(user))
                .orElseThrow(() -> new DataIntegratyViolationExceptionException("User not save. Try again!"));


    }

    public void delete(Integer id){
        Optional.ofNullable(this.findById(id))
                .ifPresent( user -> repository.deleteById(user.getId()));
    }

    private void checkIfExistEmail(String  email, Integer id){
        repository.findByEmail(email)
                .ifPresent(user -> {
                    if(!user.getId().equals(id)){
                        throw new DataIntegratyViolationExceptionException("email already exists. Please try again!");}
                });
    }


}
