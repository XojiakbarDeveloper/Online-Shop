//package uz.onlineshop.productservice.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import uz.onlineshop.authservice.entity.User;
//import uz.onlineshop.productservice.entity.base.TimeLong;
//
//@Data
//@Entity
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class Comment extends TimeLong {
//
//    private String text;
//    @ManyToOne
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;
//}
