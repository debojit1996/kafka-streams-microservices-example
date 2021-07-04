# Application Setup

* Start the docker-compose file to start the entire Kafka cluster.
* Open the control center at http://localhost:9021 and then navigate to the Topics tab to create the required topics.
* Create two topics with custom config ( replication-factor = 1) and name them as ***web-domains*** and 
  ***active.web-domains***.
* The domain-crawler application would simply expose a REST endpoint /domain/lookup/{name} . Here, {name} is a path 
  variable with possible values facebook, google etc. 
* Once we hit this endpoint with name=facebook say, we internally call this endpoint
  https://api.domainsdb.info/v1/domains/search?domain=facebook&zone=com via Spring Webclient and push all the data to the
  Kafka topic named web-domains.
* The ***domain-processor*** microservice used Kafka streams to pull these data from web-domains topic and push the 
  active domains (of facebook as in this example) into the Kafka topic active.web-domains.
* The final microservice named ***domain-service*** would simply read this data from active.web-domains topic and do a 
  system.out. However, we can enhance it and apply other functionalities here.
  
