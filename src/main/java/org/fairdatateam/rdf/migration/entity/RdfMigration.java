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


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * RdfMigration encapsulates information about concrete migration. The information is saved in the
 * database through {@link org.fairdatateam.rdf.migration.database.RdfMigrationRepository}
 *
 * @author Vojtech Knaisl (vknaisl)
 * @since 1.0.0
 */
@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RdfMigration {

    /**
     * An internal Mongo DB Id
     */
    @Id
    protected ObjectId id;

    /**
     * A number of the migration in our application
     */
    protected Integer number;

    /**
     * A name of the migration
     */
    protected String name;

    /**
     * A quick description of the purpose of the migration
     */
    protected String description;

    /**
     * A timestamp
     */
    protected LocalDateTime createdAt;

    public RdfMigration(Integer number, String name, String description) {
        this.number = number;
        this.name = name;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }

}
