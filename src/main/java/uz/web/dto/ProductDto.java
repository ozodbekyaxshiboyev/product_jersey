package uz.web.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductDto {
    private Long id;
    private String name;
    private long productType;
    private long productStatus;
    private double price;
    private String description;

}
