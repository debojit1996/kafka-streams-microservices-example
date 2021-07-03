package com.debo.domaincrawler.controller;

import com.debo.domaincrawler.service.DomainCrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/domain")
public class DomainCrawlerController {

    private final DomainCrawlerService domainCrawlerService;

    @GetMapping("/lookup/{name}")
    public String lookUp(@PathVariable(name = "name") final String name) {
        domainCrawlerService.crawl(name);
        return "Domain Crawler has scraped your data";
    }
}
