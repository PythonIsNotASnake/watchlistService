package com.pinas.watchlistService.api.controller;

import com.pinas.watchlistService.api.model.Statistic;
import com.pinas.watchlistService.handler.StatisticHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("statistic")
public class StatisticController {

    private final StatisticHandler statisticHandler;

    public StatisticController(StatisticHandler statisticHandler) {
        this.statisticHandler = statisticHandler;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Statistic getStatistic() {
        return statisticHandler.getStatistic();
    }
}
