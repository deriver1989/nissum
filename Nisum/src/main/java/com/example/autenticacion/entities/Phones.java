package com.example.autenticacion.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "phones")
public class Phones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, name = "number")
    private String number;

    @Column(nullable = false, name = "citycode")
    private String citycode;

    @Column(nullable = false, name = "contrycode")
    private String contrycode;

    public Phones(String number, String citycode, String contrycode) {
        this.number = number;
        this.citycode = citycode;
        this.contrycode = contrycode;
    }

    public Phones(Integer id, String number, String citycode, String contrycode) {
        this.id = id;
        this.number = number;
        this.citycode = citycode;
        this.contrycode = contrycode;
    }
}
