package ua.training.foodtracker.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "food")
public class Food implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "name_ua")
    private String nameUa;

    @Column(name = "carbs_mg", nullable = false)
    private Integer carbs;

    @Column(name = "protein_mg", nullable = false)
    private Integer protein;

    @Column(name = "fat_mg", nullable = false)
    private Integer fat;

    @Column(name = "calories", nullable = false)
    private Integer calories;
}
