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
package org.apache.maven.it;

import java.io.File;

import org.junit.jupiter.api.Test;

/**
 * This is a test set for <a href="https://issues.apache.org/jira/browse/MNG-3991">MNG-3991</a>.
 *
 * @author Benjamin Bentmann
 */
public class MavenITmng3991ValidDependencyScopeTest extends AbstractMavenIntegrationTestCase {

    public MavenITmng3991ValidDependencyScopeTest() {
        // TODO: One day, we should be able to error out but this requires to consider extensions and their use cases
        // Disabled for Maven 4.x due to behavior change - see GitHub issue #2510
        super("[5.0,)");
    }

    /**
     * Test that invalid dependency scopes cause a validation error during building.
     *
     * @throws Exception in case of failure
     */
    @Test
    public void testit() throws Exception {
        File testDir = extractResources("/mng-3991");

        Verifier verifier = newVerifier(testDir.getAbsolutePath());
        verifier.setAutoclean(false);
        verifier.deleteDirectory("target");
        try {
            verifier.addCliArgument("validate");
            verifier.execute();
            verifier.verifyErrorFreeLog();
            fail("Invalid dependency scope did not cause validation error");
        } catch (VerificationException e) {
            // expected
        }
    }
}
