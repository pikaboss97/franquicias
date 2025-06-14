package co.com.bancolombia.model.branchproduct;

import lombok.Data;
//import lombok.NoArgsConstructor;

@Data
public class BranchProduct {

    private Long id;
    private Integer branchId;
    private Integer productId;
    private Integer stock;
}
