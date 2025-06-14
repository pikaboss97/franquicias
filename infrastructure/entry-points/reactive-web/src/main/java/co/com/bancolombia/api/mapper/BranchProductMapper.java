package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.BranchProductDTO;
import co.com.bancolombia.model.branchproduct.BranchProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BranchProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "branchId", target = "branchId")
    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "stock", target = "stock")
    BranchProduct branchProductDTOToBranchProduct (BranchProductDTO branchProductDTO);
}
