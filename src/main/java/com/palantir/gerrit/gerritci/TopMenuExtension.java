package com.palantir.gerrit.gerritci;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.gerrit.extensions.annotations.PluginName;
import com.google.gerrit.extensions.webui.GerritTopMenu;
import com.google.gerrit.extensions.webui.TopMenu;
import com.google.inject.Inject;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;

public class TopMenuExtension implements TopMenu {

    private static final Logger logger = LoggerFactory.getLogger(TopMenuExtension.class);

    private final List<MenuEntry> menuEntries;

    @Inject
    public TopMenuExtension(@PluginName String name) {
        menuEntries = Lists.newArrayList();
        menuEntries.add(new MenuEntry(GerritTopMenu.PROJECTS,
            Collections.singletonList(new MenuItem("Gerrit-CI", "#/x/" + name + "/settings"))));
    }

    @Override
    public List<MenuEntry> getEntries() {
        // NOTE: This is here for testing purposes because I don't have a better place to put it.
        Map<String, Job> jobs = JenkinsManager.getJobs();
        for(String key: jobs.keySet()) {
            try {
                JobWithDetails job = jobs.get(key).details();
                logger.info("Name: {} Url: {}", job.getName(), job.getUrl());
            } catch(IOException e) {
                logger.error("Error getting details for job: {}", key, e);
            }

        }

        return menuEntries;
    }
}
