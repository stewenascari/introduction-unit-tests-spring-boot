package br.com.introductionunittests.introductionunittestsspringboot.services;

import br.com.introductionunittests.introductionunittestsspringboot.entities.Useer;
import br.com.introductionunittests.introductionunittestsspringboot.models.UseerDTO;
import br.com.introductionunittests.introductionunittestsspringboot.models.UseerRequestDTO;

import java.util.List;

public interface UseerService {

    UseerDTO findById(Integer id);

    Useer findByEntityId(Integer id);

    List<UseerDTO> findAll();

    UseerDTO create(UseerRequestDTO dto);

    void delete(Integer id);

    void update(Integer id, UseerRequestDTO dto);
}
