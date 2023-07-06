package com.example.demo.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "long_description")
    private String longDescription;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "rating")
    private int rating;

    public ProductEntity(Long id, byte[] photo, String longDescription, String shortDescription, String name, BigDecimal price, int rating) {
        this.id = id;
        this.photo = photo;
        this.longDescription = longDescription;
        this.shortDescription = shortDescription;
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return rating == that.rating && Objects.equals(id, that.id) && Arrays.equals(photo, that.photo) && Objects.equals(longDescription, that.longDescription) && Objects.equals(shortDescription, that.shortDescription) && Objects.equals(name, that.name) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, longDescription, shortDescription, name, price, rating);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }
}
