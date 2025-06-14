package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.BranchDTO;
import co.com.bancolombia.api.dto.ProductDTO;
import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BranchMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "franchiseId", target = "franchiseId", ignore = true)
    Branch branchDTOToBranch(BranchDTO productDTO);
}
