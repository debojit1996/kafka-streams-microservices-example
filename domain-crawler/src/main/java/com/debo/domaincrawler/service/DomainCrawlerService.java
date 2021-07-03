package com.debo.domaincrawler.service;

import com.debo.domaincrawler.model.Domain;
import com.debo.domaincrawler.model.DomainList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DomainCrawlerService {
    private final KafkaTemplate<String, Domain> kafkaTemplate;
    private static final String KAFKA_TOPIC = "web-domains";

    public void crawl(String name) {
        Mono<DomainList> domainListMono = WebClient.create()
                .get()
                .uri("https://api.domainsdb.info/v1/domains/search?domain=" + name + "&zone=com")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(DomainList.class);
        domainListMono.subscribe(
                domainList -> domainList.getDomains()
                        .forEach(domain -> {
                            kafkaTemplate.send(KAFKA_TOPIC, domain);
                            System.out.println("Domain message" + domain.getDomain());
                        })
        );

    }
}
