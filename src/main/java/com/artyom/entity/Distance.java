package com.artyom.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "distances")
public class Distance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Cascade(SAVE_UPDATE)
    @JoinColumn(name = "from_city_id")
    private City fromCity;

    @ManyToOne
    @Cascade(SAVE_UPDATE)
    @JoinColumn(name = "to_city_id")
    private City toCity;

    @Column(name = "distance")
    private Double distance;

    public Distance(City fromCity, City toCity, Double distance) {
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.distance = distance;
    }
}
