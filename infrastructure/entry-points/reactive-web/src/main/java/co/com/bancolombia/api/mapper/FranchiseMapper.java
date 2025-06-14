package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.FranchiseDTO;
import co.com.bancolombia.model.franchise.Franchise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FranchiseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "name")
    Franchise franchiseDTOToFranchise(FranchiseDTO franchiseDTO);
}
