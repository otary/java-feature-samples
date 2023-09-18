package cn.chenzw.java8.feature.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Objects;

public class Book {

    private Long id;
    private String name;
    private Double price;

    private List<BookLabel> labels;

    public Book(Long id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Book(Long id, String name, Double price, List<BookLabel> labels) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.labels = labels;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<BookLabel> getLabels() {
        return labels;
    }

    public void setLabels(List<BookLabel> labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id.equals(book.id) &&
                name.equals(book.name) &&
                price.equals(book.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }


    @Data
    @Builder
    public static class BookLabel {

        private Long id;

        private String name;
    }
}
