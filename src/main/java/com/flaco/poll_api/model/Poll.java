package com.flaco.poll_api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "polls")
@Getter
@Setter
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    // Many-to-One relationship with Option
    // orphanRemoval = true: if an Option is removed it will be deleted from DB.
    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // To prevent loop during JSON serialization
    private Set<Option> options = new HashSet<>();

    // Helper method to add a safe option
    public void addOption(Option option) {
        options.add(option);
        option.setPoll(this);
    }
}