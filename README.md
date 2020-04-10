This project creates a free text search, i.e to allow search on natural language on hospital records.
1. logstash.conf is used to upload csv data in elasticsearch.
2. elasticsearch_script has various scripts to create mappings of index.This project utilizes hospitallocator index mapping.
3. The application runs on port 8080.To run the application elasticsearch needs to be setup and must be started.
4. Thymeleaf is used for creating UI.
5. Java high rest client is used to query on ElasticSearch 7.5 in spring boot application.
6. es7_udemy is notes that I prepared while working with this module.It explains most of the needed terminologies.
