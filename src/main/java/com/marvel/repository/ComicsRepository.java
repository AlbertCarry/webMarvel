package com.marvel.repository;

import com.marvel.model.Characters;
import com.marvel.model.Comics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
//
public interface ComicsRepository extends JpaRepository<Comics, Long> {
    @Query(value = "select comics.* from characters_comics " +
            " INNER JOIN characters ON (characters_comics.characters_id = characters.id) " +
            "    INNER JOIN comics ON (characters_comics.comics_id = comics.id) " +
            "    where characters.id = ?1",nativeQuery = true)
    List<Optional<Comics>> findAllCharactersInComicsById(Long id);

    @Query(value = "select * from comics WHERE comictitle LIKE %?1% "+ "OR issue LIKE %?1% OR writer LIKE %?1% OR penciler LIKE %?1% OR published LIKE %?1% "+
            "OR created LIKE %?1%",nativeQuery = true)
    Page<Comics> pageAllQuery(String some,Pageable pageable);

    Page<Comics>findAll(Pageable pageable);
    List<Comics>findAll();

    @Query(value = "select * from comics WHERE comictitle=?1 AND issue=?2 AND published =?3",nativeQuery = true)
    Comics getByThree(String comicTitle,String issue, String published);

}
