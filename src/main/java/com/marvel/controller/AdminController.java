package com.marvel.controller;

import com.marvel.config.ResponseConfig;
import com.marvel.model.Characters;
import com.marvel.model.CharactersComics;
import com.marvel.model.Comics;
import com.marvel.repository.CharactersComicsRepository;
import com.marvel.service.CharactersService;
import com.marvel.service.ComicsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Controller
@RequestMapping("/admin")
@Api(value = "CRUD operation")
public class AdminController {
    private final CharactersService charactersService;
    private final ComicsService comicsService;
    private final CharactersComicsRepository charactersComicsRepository;
    @Autowired
    public AdminController(CharactersService charactersService, ComicsService comicsService, CharactersComicsRepository charactersComicsRepository) {
        this.charactersService = charactersService;
        this.comicsService = comicsService;
        this.charactersComicsRepository = charactersComicsRepository;
    }

    @Value("${upload.path.characters}")
    private String uploadPathCharacters;
    @Value("${upload.path.comics}")
    private String uploadPathComics;


    @GetMapping()
    @ApiOperation(value = "Return Admin page")
    public String meth(){
        return "admin/admin";

    }
//----------------------------------CreateCharacter-----------------------------------------------

    @GetMapping("characters/create")
    @ApiOperation(value = "Return  page create character")
    public String getAddCharacter(@RequestParam(value = "hardCase",defaultValue = "1") Integer hardCase
            ,Characters characters,Model model) {
        if(hardCase==0){
            model.addAttribute("hardCase",hardCase);
        }
        if(hardCase==1) {
            model.addAttribute("hardCase", hardCase);
        }
        if(hardCase!=0 && hardCase != 1){
            throw new ResponseConfig.BadRequest();
        }
        return "admin/chars/char_create";
    }

    @PostMapping("characters/create")
    @ApiOperation(value = "Post character in db")
    public String postAddCharacter(@RequestParam(value = "hardCase",defaultValue = "1") Integer hardCase
            ,Characters characters,Model model) {
        model.addAttribute("hardCase",hardCase);
       if(charactersService.getByAliases(characters.getAliases())){
           return "redirect:/admin/characters/create?hardCase=0";
       }
        charactersService.add(characters);
        return "redirect:/admin";
    }

//----------------------------------UpDateCharacter-----------------------------------------------

    @GetMapping("characters/update")
    @ApiOperation(value = "Return page update character")
    public String getUpDateCharacter(@RequestParam(value = "inquiry",required = false) String inquiry,
                          @RequestParam(value = "id",required = false) Long id,
                          @PageableDefault(sort = { "aliases" },size = 5,direction = Sort.Direction.DESC) Pageable pageable,
                                  Model model){
        if (inquiry!=null && !inquiry.equals("")){
            inquiry = inquiry.toUpperCase();
            model.addAttribute("chars",charactersService.getAllByValue(inquiry,pageable));
            if (id!=null) {
                model.addAttribute("chars", charactersService.getById(id));
            }
            model.addAttribute("inquiry",inquiry);
            model.addAttribute("id",id);
        }
        return "admin/chars/char_update";
    }

    @PutMapping ("characters/update")
    @ApiOperation(value = "Update character in db")
    public String putUpDateCharacter(@RequestParam(value = "inquiry",required = false) String inquiry,
            @ModelAttribute("char") Characters characters) {
//       Validation logic before update
        charactersService.upDate(characters);
        return "redirect:/admin/characters/update";
    }


//-----------------------------------DeleteCharacter----------------------------------------------

    @GetMapping("characters/delete")
    @ApiOperation(value = "Return page character for delete")
    public String getDeleteCharacter(@RequestParam(value = "inquiry",required = false)String inquiry,
                                     @PageableDefault(sort = { "aliases" },size = 5,direction = Sort.Direction.DESC)
                                             Pageable pageable,
                                     Model model) {
        if (inquiry!=null && !inquiry.equals("")) {
            inquiry = inquiry.toUpperCase();
            model.addAttribute("inquiry", inquiry);
            model.addAttribute("chars", charactersService.getAllByValue(inquiry, pageable));
        }
        return "admin/chars/char_delete";
    }

    @GetMapping("characters/delete/{id}")
    @ApiOperation(value = "Delete character from db")
    public String postDeleteCharacters(@PathVariable("id") Long id) {
        if (!charactersService.isExist(id))
            throw new ResponseConfig.Char();
        charactersService.remove(id);
        return "redirect:/admin/characters/delete";
    }


//----------------------------------AddCharacterToComic---------------------------------------
@GetMapping("characters/addCharToComic")
@ApiOperation(value = "Return page to add Character to Comics")
public String getAddCharToComic(@RequestParam(value = "queryChar",required = false)String queryChar,
                                @RequestParam(value = "queryComic",required = false)String queryComic,
                                @PageableDefault() Pageable pageable,
                                Model model){
        if (queryChar!=null && !queryChar.equals("")){
            queryChar = queryChar.toUpperCase();
            model.addAttribute("queryChar",queryChar);
            model.addAttribute("chars",charactersService.getAll(queryChar,pageable));
        }
    if (queryComic!=null && !queryComic.equals("")){
        Sort sort = Sort.by("published").ascending();
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        queryComic = queryComic.substring(0,1).toUpperCase()+queryComic.substring(1);
        model.addAttribute("queryComic",queryComic);
        model.addAttribute("comics",comicsService.getAll(queryComic,pageable));
    }

        return "admin/chars/add_char_to_comic";
}

    @PostMapping("characters/addCharToComic")
    @ApiOperation(value = "Add  Character to Comics")
    public String postCharToComic(
            @RequestParam(value = "charId",required = false) Long charId,
            @RequestParam(value = "comicId",required = false) Long comicId) {
        if (!comicsService.isExist(comicId)||!charactersService.isExist(charId)) {
            throw new ResponseConfig.Char();
        }
            CharactersComics charactersComics = new CharactersComics();
            charactersComics.setCharactersId(charId);
            charactersComics.setComicsId(comicId);
            charactersComicsRepository.save(charactersComics);

        return "redirect:/admin";
    }


//----------------------------------CreateComics-----------------------------------------------
    @GetMapping("comics/create")
    @ApiOperation(value = "Return  page create comics")
    public String getAddComics(@RequestParam(value = "hardCase",defaultValue = "1") Integer hardCase
            ,Comics comics,Model model) {
        if(hardCase==0){
            model.addAttribute("hardCase",hardCase);
        }
        if(hardCase==1) {
            model.addAttribute("hardCase", hardCase);
        }
        if(hardCase!=0 && hardCase != 1){
            throw new ResponseConfig.BadRequest();
        }
    return "admin/comics/comics_create";
}

    @PostMapping("comics/create")
    @ApiOperation(value = "Post comics in db")
    public String postAddCharacter(@RequestParam(value = "hardCase",defaultValue = "1") Integer hardCase
            ,Comics comics,Model model) {
        model.addAttribute("hardCase",hardCase);
        if(comicsService.getByThree(comics.getComicTitle(),comics.getIssue(),comics.getPublished())){
            return "redirect:/admin/comics/create?hardCase=0";
        }
        comicsService.add(comics);
        return "redirect:/admin";
    }
//----------------------------------UpDateComics-----------------------------------------------
@GetMapping("comics/update")
@ApiOperation(value = "Return page update comics")
public String getUpDateComics(@RequestParam(value = "inquiry",required = false) String inquiry,
                                 @RequestParam(value = "id",required = false) Long id,
                                 @PageableDefault(sort = { "comicTitle" },size = 5,direction = Sort.Direction.DESC) Pageable pageable,
                                 Model model){
    if (inquiry!=null && !inquiry.equals("")){
        inquiry = inquiry.toLowerCase();
        inquiry = inquiry.substring(0,1).toUpperCase()+inquiry.substring(1);
        model.addAttribute("comics",comicsService.getAll(inquiry,pageable));
        if (id!=null ) {
            model.addAttribute("comics", comicsService.getById(id));
        }
        model.addAttribute("inquiry",inquiry);
        model.addAttribute("id",id);
    }
    return "admin/comics/comics_update";
}

    @PutMapping ("comics/update")
    @ApiOperation(value = "Update comics in db")
    public String putUpDateComics(@RequestParam(value = "inquiry",required = false) String inquiry,
                                     @ModelAttribute("comics") Comics comics) {
        //       Validation logic before update
        comicsService.upDate(comics);
        return "redirect:/admin/comics/update";
    }

//-----------------------------------DeleteComics----------------------------------------------
@GetMapping("comics/delete")
@ApiOperation(value = "Return page comics for delete")
public String getDeleteComics(@RequestParam(value = "inquiry",required = false)String inquiry,
                                 @PageableDefault(sort = { "id" },direction = Sort.Direction.DESC)
                                         Pageable pageable,
                                 Model model) {
    if (inquiry!=null && !inquiry.equals("")) {
        inquiry = inquiry.toLowerCase();
        inquiry = inquiry.substring(0,1).toUpperCase()+inquiry.substring(1);
        model.addAttribute("inquiry", inquiry);
        model.addAttribute("comics", comicsService.getAll(inquiry, pageable));
    }
    return "admin/comics/comics_delete";
}

    @GetMapping("comics/delete/{id}")
    @ApiOperation(value = "Delete comics from db")
    public String postDeleteComics(@PathVariable("id") Long id) {
        if (!comicsService.isExist(id))
            throw new ResponseConfig.Char();
        comicsService.remove(id);
        return "redirect:/admin/comics/delete";
    }


//----------------------------------AddPicture-----------------------------------------------

    @GetMapping("picture/add")
    @ApiOperation(value = "Return page for upload picture")
    public String addPicture(@RequestParam(value = "inquiry",required = false)String inquiry,
                             Model model){
        model.addAttribute("inquiry",inquiry);
        return "admin/add_picture";
    }

    @PostMapping("picture/add")
    @ApiOperation(value = "Upload picture")
    public String downLoadPicture(@RequestParam(value = "inquiry",required = false)String inquiry,
                                  @RequestParam(value = "name",required = false)String name,
                                  @RequestParam(value = "created",required = false)String created,
                                  @RequestParam(value = "issue",required = false)String issue,
                                  @RequestParam(value = "file",required = false) MultipartFile file)
            throws IOException {
        //       Need to check before uploading
       if(inquiry!=null && !inquiry.equals("")){
           if(inquiry.equals("characters")){
               String finishFileName = name.toUpperCase()+".jpg";
               file.transferTo(new File(uploadPathCharacters+"/"+finishFileName));
           }
           if(inquiry.equals("comics")){
               String finishFileName = name.substring(0,1).toUpperCase()+name.substring(1)+created+issue+".jpg";
               file.transferTo(new File(uploadPathComics+"/"+finishFileName));
           }
       }
        return "redirect:/admin/picture/add";
    }



}
