/*
 * The MIT License
 * Copyright © 2019 DTL
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.fairdatateam.rdf.migration.runner;

import lombok.extern.slf4j.Slf4j;
import org.fairdatateam.rdf.migration.database.RdfMigrationMongoRepository;
import org.fairdatateam.rdf.migration.entity.MongoRdfMigration;
import org.fairdatateam.rdf.migration.entity.RdfMigrationAnnotation;
import org.springframework.context.ApplicationContext;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * The class contains a logic about executing the migration
 *
 * @author Vojtech Knaisl (vknaisl)
 * @since 1.0.0
 */
@Slf4j
public class RdfProductionMigrationRunner {

    /**
     * A repository for storing information about metadata into a database
     */
    private RdfMigrationMongoRepository rdfMigrationRepository;

    /**
     * A Spring application context that is needed to retrieve a concrete annotated migration class defined by a user
     */
    private ApplicationContext appContext;

    public RdfProductionMigrationRunner(RdfMigrationMongoRepository rdfMigrationRepository,
                                        ApplicationContext appContext) {
        this.rdfMigrationRepository = rdfMigrationRepository;
        this.appContext = appContext;
    }

    /**
     * The method executes a migration process. It loads all migrations from the Spring Application context, filters
     * them with already completed annotation, and runs the migrations that weren't run yet.
     */
    public void run() {
        log.info("Production Migration of RDF Store started");
        List<MongoRdfMigration> migrationsInDB = rdfMigrationRepository.findAll();
        int lastMigrationNumber = migrationsInDB
                .stream()
                .map(MongoRdfMigration::getNumber)
                .max(Integer::compareTo)
                .orElse(0);

        appContext.getBeansWithAnnotation(RdfMigrationAnnotation.class)
                .values()
                .stream()
                .map(o -> {
                    if (!(o instanceof RdfProductionMigration)) {
                        log.error("Defined Migration has to be type of RdfProductionMigration");
                        return null;
                    }
                    return (RdfProductionMigration) o;
                })
                .filter(Objects::nonNull)
                .filter(m -> getAnnotation(m).number() > lastMigrationNumber)
                .sorted(Comparator.comparingInt(m -> getAnnotation(m).number()))
                .forEach(m -> {
                    MongoRdfMigration mEntity = new MongoRdfMigration(getAnnotation(m).number(),
                            getAnnotation(m).name(),
                            getAnnotation(m).description());
                    log.info("Production Migration (n. {}) started", mEntity.getNumber());
                    m.runMigration();
                    rdfMigrationRepository.save(mEntity);
                    log.info("Production Migration (n. {}) ended", mEntity.getNumber());
                });
        log.info("Production Migration of RDF Store ended");
    }

    /**
     * A helper method for retrieving an annotation from the provided object
     *
     * @param migration A migration from which we want to extract annotation
     * @return The desired annotation
     */
    private RdfMigrationAnnotation getAnnotation(RdfProductionMigration migration) {
        return migration.getClass().getAnnotation(RdfMigrationAnnotation.class);
    }

}

