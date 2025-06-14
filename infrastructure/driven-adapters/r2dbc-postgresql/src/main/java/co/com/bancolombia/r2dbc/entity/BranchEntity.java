package co.com.bancolombia.r2dbc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("branch")
public class BranchEntity {

    @Id
    private Integer id;
    private String name;
    private Integer franchiseId;
}
