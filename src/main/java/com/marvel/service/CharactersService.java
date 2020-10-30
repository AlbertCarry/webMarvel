package com.marvel.service;

import com.marvel.model.Characters;
import com.marvel.model.Comics;
import com.marvel.repository.CharactersRepository;
import com.marvel.repository.ComicsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CharactersService {
    private final CharactersRepository charactersRepository;
    private final ComicsRepository comicsRepository;
    @Autowired
    public CharactersService(CharactersRepository charactersRepository, ComicsRepository comicsRepository) {
        this.charactersRepository = charactersRepository;
        this.comicsRepository = comicsRepository;
    }
    public Characters add(Characters characters) {
        return charactersRepository.save(characters);
    }
    public List<Characters> getAll() {
        return charactersRepository.findAll();
    }
    public Page<Characters> getAll(Pageable pageable) {
        return charactersRepository.findAll(pageable);
    }
    public void upDate(Characters characters){
        charactersRepository.save(characters);
    }
    public void remove(Long id) {
        charactersRepository.deleteById(id);
    }
    public Characters getById(Long id){
            return charactersRepository.getOne(id);
    }
    public boolean getByAliases(String aliases){
        aliases = aliases.toUpperCase();
      return charactersRepository.getByAliases(aliases) != null;
    }
    public List<Comics> byComics(Long id) {
        List<Optional<Comics>> optionalComics = comicsRepository.findAllCharactersInComicsById(id);
        List<Comics> comicsList = new ArrayList<>();
        for (Optional<Comics> comics: optionalComics){
            if (comics.isPresent()){
                comicsList.add(comics.get());
            }
        }
        return comicsList;
    }
    public Page<Characters> getAll(String some, Pageable pageable) {
        return charactersRepository.getAllByValue(some,pageable);
    }
    public Page<Characters> getAllByValue(String value,Pageable pageable){
        return charactersRepository.getAllByValue(value,pageable);
    }
    public boolean isExist(Long id){
        return charactersRepository.existsById(id);
    }

}
