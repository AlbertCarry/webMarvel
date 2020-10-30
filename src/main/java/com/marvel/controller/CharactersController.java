package com.marvel.controller;


import com.marvel.config.ResponseConfig;
import com.marvel.model.Characters;
import com.marvel.service.CharactersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;




@Controller
@RequestMapping("/characters")
@Api(value = "Getting characters")
public class CharactersController {

    private final CharactersService charactersService;
    @Autowired
    public CharactersController(CharactersService charactersService) {
        this.charactersService = charactersService;

    }


    @GetMapping()
    @ApiOperation(value = "Returns characters")
    public String getAllCharacters(
            @PageableDefault(sort = { "aliases" },size = 6,direction = Sort.Direction.DESC)
                                            Pageable pageable,Model model) {
        Page<Characters> page = charactersService.getAll(pageable);
        if (page.isEmpty()  || page.getPageable().getPageNumber()+1>page.getTotalPages())
            throw new ResponseConfig.Char();
        model.addAttribute("countPage",page.getTotalPages());
        model.addAttribute("page",page);
        return "chars/chars_list";
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Returns character by id")
    public String getCharactersById(@PathVariable("id") Long id, Model model) {
        if (!charactersService.isExist(id))
        throw new ResponseConfig.Char();
            model.addAttribute("chars", charactersService.getById(id));
            return "chars/chars_by_id";
    }

    @GetMapping("/{id}/in-comics")
    @ApiOperation(value = "Returns full character report")
    public String getCharactersFullReport(@PathVariable("id") Long id, Model model) {
        if (!charactersService.isExist(id))
            throw new ResponseConfig.Char();
        model.addAttribute("chars", charactersService.getById(id));
        return "/chars/chars_in_comics_full_report";
    }

    @GetMapping("/{id}/in-comics/profile")
    @ApiOperation(value = "Returns comics  where the character is present")
    public String getCharactersInComics(
            @PathVariable("id") Long id,
            Model model) {
        if (!charactersService.isExist(id))
            throw new ResponseConfig.Char();
        Map<String, Object> charactersServiceMap = new HashMap<>();
        charactersServiceMap.put("comics", charactersService.byComics(id));
        charactersServiceMap.put("chars", charactersService.getById(id));
        model.addAllAttributes(charactersServiceMap);
        return "chars/chars_in_comics_profile";
    }

}

