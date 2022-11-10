package br.com.introductionunittests.introductionunittestsspringboot.models;

import br.com.introductionunittests.introductionunittestsspringboot.entities.Useer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UseerDTO {

    private Integer id;
    private String name;
    private String email;
    @JsonIgnore
    private String password;

    public static UseerDTO toEntity(Useer entity){
        return UseerDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .build();
    }
}
