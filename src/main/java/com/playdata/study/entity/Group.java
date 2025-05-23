package com.playdata.study.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_group")

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"idols", "albums"})
@EqualsAndHashCode(of = "id")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;

    private String groupName;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Album> albums = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Idol> idols = new ArrayList<>();

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public void addIdol(Idol idol) {
        idols.add(idol);
        idol.setGroup(this);
    }

    public void removeIdol(Idol idol) {
        idols.remove(idol);
        idol.setGroup(null);
    }
}
