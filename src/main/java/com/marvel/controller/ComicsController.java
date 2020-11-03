package com.marvel.controller;


import com.marvel.config.ResponseConfig;
import com.marvel.model.Comics;
import com.marvel.service.ComicsServiceImpl;
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

@Controller
@RequestMapping("/comics")
@Api(value = "Getting comics")
public class ComicsController {
    private final ComicsServiceImpl comicsServiceImpl;

    @Autowired
    public ComicsController(ComicsServiceImpl comicsServiceImpl) {
        this.comicsServiceImpl = comicsServiceImpl;
    }


    @GetMapping()
    @ApiOperation(value = "Returns comics")
    public String getAllComics(Model model,
                               @PageableDefault(sort = {"selled"}, size = 6, direction = Sort.Direction.DESC)
                                       Pageable pageable) {
        Page<Comics> page = comicsServiceImpl.getAll(pageable);
        if (page.isEmpty() || page.getPageable().getPageNumber() + 1 > page.getTotalPages())
            throw new ResponseConfig.Comic();
        model.addAttribute("countPage", page.getTotalPages());
        model.addAttribute("page", page);
        return "comics/comics_list";
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Returns comic by id")
    public String getComicsById(@PathVariable("id") Long id, Model model) {
        if (!comicsServiceImpl.isExist(id))
            throw new ResponseConfig.Comic();
        model.addAttribute("comics", comicsServiceImpl.getById(id));
        return "comics/comics";
    }

    @GetMapping("/{id}/characters")
    @ApiOperation(value = "Returns characters present in comic")
    public String getAllMainCharacters(@PathVariable("id") Long id, Model model) {
        if (!comicsServiceImpl.isExist(id))
            throw new ResponseConfig.Comic();
        model.addAttribute("chars", comicsServiceImpl.byComics(id));
        return "comics/comics_chars";
    }
}
