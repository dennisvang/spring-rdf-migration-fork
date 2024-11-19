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
package org.fairdatateam.rdf.migration.entity;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * A record in the migration history of an RDF triple-store.
 * 
 * The migration history itself is stored in a relational database, for example PostgreSQL,
 * via the {@link org.fairdatateam.rdf.migration.database.RdfMigrationCrudRepository} 
 * repository interface.
 * 
 * @author dennisvang
 * @since 2.1.0
 */
@Data
@RequiredArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class RdfMigrationJpa {

    /**
     * An auto-generated internal database record id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * An auto-generated creation timestamp
     */
    // CreatedDate requires auditing config:
    // https://docs.spring.io/spring-data/jpa/reference/auditing.html#jpa.auditing.configuration
    @CreatedDate
    @Column(updatable = false)
    private Instant createdAt;

    /**
     * A migration number
     */
    @NonNull
    private Integer number;

    /**
     * A migration name
     */
    @NonNull
    private String name;

    /**
     * A migration description
     */
    @NonNull
    private String description;
    
}
