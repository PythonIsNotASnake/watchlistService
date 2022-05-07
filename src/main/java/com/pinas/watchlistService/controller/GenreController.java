package com.pinas.watchlistService.controller;

import com.pinas.watchlistService.handler.GenreHandler;
import com.pinas.watchlistService.model.Genre;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Genre> getRecords() {
        return genreHandler.getGenres();
    }
}
