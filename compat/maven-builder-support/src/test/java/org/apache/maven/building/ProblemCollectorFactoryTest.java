/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.maven.building;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class ProblemCollectorFactoryTest {

    @Test
    void testNewInstance() {
        ProblemCollector collector1 = ProblemCollectorFactory.newInstance(null);

        Problem problem = new DefaultProblem("MESSAGE1", null, null, -1, -1, null);
        ProblemCollector collector2 = ProblemCollectorFactory.newInstance(Collections.singletonList(problem));

        assertNotSame(collector1, collector2);
        assertEquals(0, collector1.getProblems().size());
        assertEquals(1, collector2.getProblems().size());
    }

    @Test
    void testAddProblem() {
        ProblemCollector collector = ProblemCollectorFactory.newInstance(null);
        collector.setSource("pom.xml");
        collector.add(Problem.Severity.ERROR, "Error message", 10, 5, null);
        collector.add(Problem.Severity.WARNING, "Warning message", 15, 3, null);

        List<Problem> problems = collector.getProblems();
        assertEquals(2, problems.size(), "Should collect both problems");
        assertEquals(Problem.Severity.ERROR, problems.get(0).getSeverity(), "First problem should be ERROR");
        assertEquals("Error message", problems.get(0).getMessage(), "First problem should have correct message");
        assertEquals(Problem.Severity.WARNING, problems.get(1).getSeverity(), "Second problem should be WARNING");
    }
}
