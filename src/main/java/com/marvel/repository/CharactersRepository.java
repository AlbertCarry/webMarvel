package com.marvel.repository;

import com.marvel.model.Characters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface CharactersRepository extends JpaRepository<Characters,Long> {
    @Query(value = "select characters.* from characters_comics " +
            " INNER JOIN characters ON (characters_comics.characters_id = characters.id) " +
            "    INNER JOIN comics ON (characters_comics.comics_id = comics.id) " +
            "    where comics.id = ?1",nativeQuery = true)
    List<Optional<Characters>> findAllCharsById(Long id);

    Page<Characters>findAll(Pageable pageable);

    @Query(value = "select * from characters WHERE  " +
            "name LIKE %?1% "+ "OR secondname LIKE %?1% OR aliases LIKE %?1% " +
            "OR otheraliases LIKE %?1%",nativeQuery = true)
    Page<Characters> getAllByValue(String value,Pageable pageable);

    @Query(value = "select * from characters WHERE id = ?1",nativeQuery = true)
    Page<Characters> getPageById(Long id,Pageable pageable);

    @Query(value = "select * from characters WHERE aliases = ?1",nativeQuery = true)
    Characters getByAliases(String aliases);




}
