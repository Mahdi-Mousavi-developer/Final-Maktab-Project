package ir.maktabsharif.finalproject.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.time.LocalDateTime;


@MappedSuperclass
@Getter
@Setter

public class BaseEntity<ID extends Serializable> implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private ID id;

    @Column(name = "create_date_entity")
    private final LocalDateTime createDate = LocalDateTime.now();

}