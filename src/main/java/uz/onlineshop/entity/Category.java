package uz.onlineshop.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.onlineshop.entity.base.TimeLong;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends TimeLong {
    private String name;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;

}
