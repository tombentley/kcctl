/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.tombentley.kafctl.command;

import javax.inject.Inject;
import java.util.ArrayList;

import com.github.tombentley.kafctl.format.ListTopicsOutput;
import com.github.tombentley.kafctl.util.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import picocli.CommandLine;
import picocli.CommandLine.Option;

@CommandLine.Command(name = "topics", description = "Lists topics.")
public class GetTopics implements Runnable {

    @Option(names = {"--output", "-o"},
            defaultValue = "table",
            converter = ListTopicsOutput.OutputFormatConverter.class,
            completionCandidates = ListTopicsOutput.OutputFormatConverter.class)
    ListTopicsOutput output;

    @Inject
    AdminClient adminClient;

    @Override
    public void run() {
        adminClient.withAdmin(admin -> {
            var listing = new ArrayList<>(admin.listTopics(new ListTopicsOptions().listInternal(true)).listings().get());
            // TODO show the internal flag too?
            System.out.println(output.listTopics(listing));
            return null;
        });
    }
}
