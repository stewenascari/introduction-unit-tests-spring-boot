package br.com.introductionunittests.introductionunittestsspringboot.models.mappers;

import br.com.introductionunittests.introductionunittestsspringboot.entities.Useer;
import br.com.introductionunittests.introductionunittestsspringboot.models.UseerDTO;
import br.com.introductionunittests.introductionunittestsspringboot.models.UseerRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    UseerDTO userToUserDTO(Useer useer);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    Useer userRequestToUser(UseerRequestDTO dto);


    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    Useer userDTORequestToUserRequest(UseerDTO dto);
}
