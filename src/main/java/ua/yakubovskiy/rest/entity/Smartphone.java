package ua.yakubovskiy.rest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "smartphones", schema = "ps")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Smartphone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "model")
    private String model;

    @Column(name = "colour")
    private String colour;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
}
