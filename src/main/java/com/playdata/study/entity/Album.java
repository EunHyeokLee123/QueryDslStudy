package com.playdata.study.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_album")
@Setter
@Getter
@ToString(exclude = "group")
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long id;

    private String albumName; // 앨범명
    private int releaseYear; // 발매연도

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public Album(String albumName, int releaseYear, Group group) {
        this.albumName = albumName;
        this.releaseYear = releaseYear;
        this.group = group;
    }
}