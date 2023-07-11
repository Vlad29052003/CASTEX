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


    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "long_description")
    private String longDescription;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "category")
    private String category;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "rating")
    private int rating;

    @Column(name = "stock")
    private int stock;

    public ProductEntity(String name, byte[] photo, String longDescription, String shortDescription,
                         String category, BigDecimal price,int stock) {
        this.name = name;
        this.photo = photo;
        this.longDescription = longDescription;
        this.shortDescription = shortDescription;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public ProductEntity() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return rating == that.rating && stock == that.stock && Objects.equals(id, that.id) && Arrays.equals(photo, that.photo) && Objects.equals(longDescription, that.longDescription) && Objects.equals(shortDescription, that.shortDescription) && Objects.equals(category, that.category) && Objects.equals(name, that.name) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, longDescription, shortDescription, category, name, price, rating, stock);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getRating() {
        return rating;
    }

    public int getStock() {
        return stock;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", longDescription='" + longDescription + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", rating=" + rating +
                ", stock=" + stock +
                '}';
    }
}
