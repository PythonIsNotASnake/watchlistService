package com.pinas.watchlistService.api.controller;

import com.pinas.watchlistService.api.model.Genre;
import com.pinas.watchlistService.handler.GenreHandler;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("genres")
public class GenreController {

    private final GenreHandler genreHandler;

    public GenreController(GenreHandler genreHandler) {
        this.genreHandler = genreHandler;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Genre> getGenres() {
        return genreHandler.getGenres();
    }
}
