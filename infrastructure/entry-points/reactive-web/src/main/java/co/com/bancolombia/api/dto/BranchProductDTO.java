package co.com.bancolombia.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BranchProductDTO {

    private Long id;
    private Integer branchId;
    private Integer productId;
    private Integer stock;
}
