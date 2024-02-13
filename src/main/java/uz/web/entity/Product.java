package uz.web.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Product {
    private Long id;
    private String name;
    private ProductType productType;
    private ProductStatus productStatus;
    private double price;
    private String description;

}
