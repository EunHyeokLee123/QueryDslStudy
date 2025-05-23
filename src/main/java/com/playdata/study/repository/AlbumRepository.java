package com.playdata.study.repository;

import com.playdata.study.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {



}
