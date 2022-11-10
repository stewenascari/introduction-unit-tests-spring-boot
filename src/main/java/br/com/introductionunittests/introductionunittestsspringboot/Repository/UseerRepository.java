package br.com.introductionunittests.introductionunittestsspringboot.Repository;

import br.com.introductionunittests.introductionunittestsspringboot.entities.Useer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UseerRepository extends JpaRepository<Useer, Integer> {

    Optional<Useer> findByEmail( String email);
}
