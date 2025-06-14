package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.ProductDTO;
import co.com.bancolombia.model.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "name")
    Product productDTOToProduct(ProductDTO productDTO);
}
