package org.example.models;

import lombok.Data;

import java.util.*;
import javax.persistence.*;

@Data
@Entity
@Table(name="tbl_categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Column(length = 200, nullable = false)
    private String name;

    @Column(length = 200)
    private String image;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;
}