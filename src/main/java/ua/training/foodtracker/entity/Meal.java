package ua.training.foodtracker.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

@Entity
@Table(name = "meals")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", referencedColumnName="id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="food_id", referencedColumnName="id")
    private Food food;

    @Column(name = "amount_g", nullable = false)
    private Integer amount;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

}
