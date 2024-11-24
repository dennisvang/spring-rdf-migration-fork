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
package org.fairdatateam.rdf.migration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.fairdatateam.rdf.migration.database.RdfMigrationCrudRepository;
import org.fairdatateam.rdf.migration.entity.RdfMigrationAnnotation;
import org.fairdatateam.rdf.migration.runner.JpaRdfMigrationRunner;
import org.fairdatateam.rdf.migration.runner.Migratable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureTestDatabase
public class JpaRdfMigrationRunnerTests {

    @Autowired
    private RdfMigrationCrudRepository repository;

    @Autowired
    private JpaRdfMigrationRunner runner;

    @Test
    public void testRun() {
        runner.run();
        assertEquals(1, repository.count());
    }

    @SpringBootApplication
    @EnableJpaAuditing
    static class TestConfiguration {
        // for lack of an actual application
    }

    @RdfMigrationAnnotation(number = 1, name = "test", description = "test")
    @Service
    private class TestMigration implements Migratable {
        public void runMigration() {
            log.info("running migration");
        }
    }
}
