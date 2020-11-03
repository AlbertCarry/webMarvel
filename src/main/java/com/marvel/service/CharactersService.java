package com.marvel.service;

import com.marvel.model.Characters;
import com.marvel.model.Comics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CharactersService {
     Characters add(Characters characters);
     void update(Characters characters);
     void remove(Long id);
     Characters getById(Long id);
     boolean getByAliases(String aliases);
     List<Comics> byComics(Long id);
     Page<Characters> getAll(Pageable pageable);
     Page<Characters> getAll(String some, Pageable pageable);
     Page<Characters> getAllByValue(String value, Pageable pageable);
     boolean isExist(Long id);
}
