package ua.training.foodtracker.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "food_info")
public class FoodInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
    @JoinColumn(name="food_id", referencedColumnName="id")
    private Food food;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="adder_user_id", referencedColumnName="id")
    private User user;

    private Boolean isGlobal;

}
