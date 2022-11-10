package br.com.introductionunittests.introductionunittestsspringboot.config;

import br.com.introductionunittests.introductionunittestsspringboot.Repository.UseerRepository;
import br.com.introductionunittests.introductionunittestsspringboot.entities.Useer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UseerRepository repository;

    @Bean
    public void startDB(){
        Useer ul = new Useer(null, "nome teste 1", "teste1@gmail.com", "123");
        Useer ul2 = new Useer(null, "nome teste 2", "teste2@gmail.com", "123");

        repository.saveAll(List.of(ul, ul2));
    }
}
