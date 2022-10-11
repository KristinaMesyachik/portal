package com.example.demo.entity;

import com.example.demo.entity.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "field", schema = "portal")
public class Field {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "label")
    private String label;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @NotNull
    @Column(name = "is_required")
    private Boolean isRequired;

    @NotNull
    @Column(name = "is_active")
    private Boolean isActive;


    @OneToMany(mappedBy = "fieldId"
            , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options;
}
