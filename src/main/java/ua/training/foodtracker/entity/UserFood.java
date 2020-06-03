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
@Table(name = "users_food")
public class UserFood {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="username", referencedColumnName="username")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="foodname", referencedColumnName="name")
    private Food food;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

}
