package com.marvel.service;

import com.marvel.model.Characters;
import com.marvel.model.Comics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ComicsService {
    Comics add(Comics comics);
    void update(Comics comics);
    void remove(Long id);
    Comics getById(Long id);
    List<Characters> byComics(Long id);
    Page<Comics> getAll(Pageable pageable);
    Page<Comics> getAll(String some,Pageable pageable);
    boolean isExist(Long id);
    boolean getByThree(String comicTitle,String issue, String published);
}
