package co.com.bancolombia.r2dbc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("branch_product")
public class BranchProductEntity {
    @Id
    private Integer id;

    @Column("branch_id")
    private Integer branchId;

    @Column("product_id")
    private Integer productId;

    private Integer stock;
}
