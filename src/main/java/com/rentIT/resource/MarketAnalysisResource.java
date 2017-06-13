package com.rentIT.resource;

import com.rentIT.domain.model.ChartResponse;
import com.rentIT.service.MarketAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analysis")
public class MarketAnalysisResource {

    private final MarketAnalysisService marketAnalysisService;

    @Autowired
    public MarketAnalysisResource(MarketAnalysisService marketAnalysisService) {
        this.marketAnalysisService = marketAnalysisService;
    }

    @RequestMapping(path = "/bucharest", method = RequestMethod.GET)
    public ResponseEntity getMarketAnalysisForBucharest() {
        return new ResponseEntity<>(
                marketAnalysisService.getAnalysisForBucharest(), HttpStatus.OK);
    }

    @RequestMapping(path = "/cities", method = RequestMethod.GET)
    public ResponseEntity<ChartResponse> getMarketAnalysisForEachCity() {
        return new ResponseEntity<>(marketAnalysisService.getAnalysisForEachCity(), HttpStatus.OK);
    }

}
