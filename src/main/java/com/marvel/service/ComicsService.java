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
public class ComicsService {
    private final ComicsRepository comicsRepository;
    private final CharactersRepository charactersRepository;
    @Autowired
    public ComicsService(ComicsRepository comicsRepository, CharactersRepository charactersRepository) {
        this.comicsRepository = comicsRepository;
        this.charactersRepository = charactersRepository;
    }
    public Page<Comics> getAll(Pageable pageable) {
        return comicsRepository.findAll(pageable);
    }
    public Page<Comics> getAll(String some,Pageable pageable) {
        return comicsRepository.pageAllQuery(some,pageable);
    }
    public Comics getById(Long id)  {
        return comicsRepository.getOne(id);
    }
    public Comics add(Comics comics) {
        return comicsRepository.save(comics);
    }
    public void upDate(Comics comics){
        comicsRepository.save(comics);
    }
    public void remove(Long id) {
        comicsRepository.deleteById(id);
    }
    public List<Characters> byComics(Long id) {
        List<Optional<Characters>> optionalComics = charactersRepository.findAllCharsById(id);
        List<Characters> charactersList = new ArrayList<>();
        for (Optional<Characters> chars: optionalComics){
            chars.ifPresent(charactersList::add);
        }
        return charactersList;
    }
    public boolean isExist(Long id) {
        return comicsRepository.existsById(id);
    }
    public boolean getByThree(String comicTitle,String issue, String published){
        return comicsRepository.getByThree(comicTitle,issue,published) != null;
    }

}
