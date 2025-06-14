package co.com.bancolombia.r2dbc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("franchise")
public class FranchiseEntity {

    @Id
    private Integer id;
    private String name;
}
