# Spring RDF Migration

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE.md)

Spring RDF Migration Library should help users to maintain migrations for their Triple Stores.

## Usage

Firstly, you need to define a migration runner bean in your configuration.

```java
@Bean
public RdfProductionMigrationRunner rdfProductionMigrationRunner(RdfMigrationRepository rdfMigrationRepository,
                                                                 ApplicationContext appContext) {
    RdfProductionMigrationRunner mr = new RdfProductionMigrationRunner(rdfMigrationRepository, appContext);
    mr.run();
    return mr;
}
```

You have to adjust your MongoDB configuration to include entities and repositories from *Spring RDF Migration* library.

```java
@Configuration
@EnableMongoRepositories(basePackages = {"com.example", "org.fairdatateam.rdf.migration"})
public class MongoConfig {
}
```

That's all! You can start creating your migrations. See the example below.

```java

@RdfMigrationAnnotation(
    number = 1,
    name = "Init migration",
    description = "Load initial data into Triple Store")
@Service
public class Rdf_Migration_0001_Init implements RdfProductionMigration {

    @Autowired
    protected Repository repository;
    
    public void runMigration() {
      // your code
    }

}
```



## How to contribute

### Install requirements

**Stack:**

 - **Java** (recommended JDK 17)
 - **Maven** (recommended 3.2.5 or higher)

### Package the application

Run these commands from the root of the project

```bash
$ mvn package
```

### Build documentation

Run these commands from the root of the project

```bash
$ mvn javadoc:javadoc
```
