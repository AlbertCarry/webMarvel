package com.marvel.service;

import com.marvel.model.Characters;
import com.marvel.model.Comics;
import com.marvel.repository.CharactersRepository;
import com.marvel.repository.ComicsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CharactersServiceImpl implements CharactersService {
    private final CharactersRepository charactersRepository;
    private final ComicsRepository comicsRepository;

    @Autowired
    public CharactersServiceImpl(CharactersRepository charactersRepository, ComicsRepository comicsRepository) {
        this.charactersRepository = charactersRepository;
        this.comicsRepository = comicsRepository;
    }

    @Override
    public Characters add(Characters characters) {
        return charactersRepository.save(characters);
    }

    @Override
    public Page<Characters> getAll(Pageable pageable) {
        return charactersRepository.findAll(pageable);
    }

    @Override
    public void update(Characters characters) {
        charactersRepository.save(characters);
    }

    @Override
    public void remove(Long id) {
        charactersRepository.deleteById(id);
    }

    @Override
    public Characters getById(Long id) {
        return charactersRepository.getOne(id);
    }

    @Override
    public boolean getByAliases(String aliases) {
        aliases = aliases.toUpperCase();
        return charactersRepository.getByAliases(aliases) != null;
    }

    @Override
    public List<Comics> byComics(Long id) {
        List<Optional<Comics>> optionalComics = comicsRepository.findAllCharactersInComicsById(id);
        List<Comics> comicsList = new ArrayList<>();
        for (Optional<Comics> comics : optionalComics) {
            if (comics.isPresent()) {
                comicsList.add(comics.get());
            }
        }
        return comicsList;
    }

    @Override
    public Page<Characters> getAll(String some, Pageable pageable) {
        return charactersRepository.getAllByValue(some, pageable);
    }

    @Override
    public Page<Characters> getAllByValue(String value, Pageable pageable) {
        return charactersRepository.getAllByValue(value, pageable);
    }

    @Override
    public boolean isExist(Long id) {
        return charactersRepository.existsById(id);
    }

}
