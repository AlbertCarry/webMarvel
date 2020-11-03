package com.marvel.controller;



import com.marvel.service.CharactersServiceImpl;
import com.marvel.service.ComicsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/search")
@Api(value = "Search Comic or Character")
public class SearchController {

    private final CharactersServiceImpl charactersServiceImpl;
    private final ComicsServiceImpl comicsServiceImpl;

    @Autowired
    public SearchController(CharactersServiceImpl charactersServiceImpl, ComicsServiceImpl comicsServiceImpl) {
        this.charactersServiceImpl = charactersServiceImpl;
        this.comicsServiceImpl = comicsServiceImpl;
    }

    @GetMapping()
    @ApiOperation(value = "Return Comics or Characters from request")
    public String show(@RequestParam(value = "content",required = false) String content,
                       @RequestParam(value = "query",required = false) String query,
                       @RequestParam(value = "sortBy",required = false,defaultValue = "id") String sortBy,
                       @RequestParam(value = "directionBy",required = false,defaultValue = "ASC") String directionBy,
                       @PageableDefault(size = 6) Pageable pageable,
                       Model model){

        Sort sort = Sort.by(sortBy).ascending();
        if(directionBy.equals("DESC")){
             sort = Sort.by(sortBy).descending();
        }
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        model.addAttribute("query", query);
        model.addAttribute("content",content);
        model.addAttribute("sortBy",sortBy);
        if (content != null){
            switch (content) {
                case ("all"):
                    if(query!=null && !query.equals("") ){
                        query = query.toLowerCase();
                        query = query.substring(0, 1).toUpperCase() + query.substring(1).toLowerCase();
                        model.addAttribute("content",content);
                        model.addAttribute("query",query);

                        model.addAttribute("pageChar", charactersServiceImpl.getAll(query,pageable));
                        model.addAttribute("pageCom", comicsServiceImpl.getAll(query,pageable));
                    }else {
                        model.addAttribute("pageChar", charactersServiceImpl.getAll(pageable));
                        model.addAttribute("pageCom", comicsServiceImpl.getAll(pageable));
                    }
                    break;
                case ("characters"):
                    if(query!=null && !query.equals("")){
                        query = query.toUpperCase();
                        model.addAttribute("content",content);
                        model.addAttribute("query",query);
                        model.addAttribute("page", charactersServiceImpl.getAll(query,pageable));
                    }else {
                        model.addAttribute("page", charactersServiceImpl.getAll(pageable));
                    }
                    break;
                case ("comics"):
                    if(query!=null && !query.equals("")){
                        query = query.toLowerCase();
                        query = query.substring(0, 1).toUpperCase() + query.substring(1).toLowerCase();
                        model.addAttribute("content",content);
                        model.addAttribute("query",query);
                        model.addAttribute("page", comicsServiceImpl.getAll(query,pageable));
                    }else {
                        model.addAttribute("page", comicsServiceImpl.getAll(pageable));
                    }
                    break;
            }
        }
        return "search/search";
    }


}
