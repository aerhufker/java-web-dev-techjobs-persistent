package org.launchcode.persistent.controllers;
import org.launchcode.persistent.models.data.JobRepository;

import org.launchcode.persistent.models.Job;
//import org.launchcode.persistent.models.data.JobRepository;
import org.launchcode.persistent.models.JobData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;


/**
 * Created by LaunchCode
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping(value = "list")
public class ListController {

    @Autowired
    private JobRepository jobRepository;

    static HashMap<String, String> columnChoices = new HashMap<>();

    public ListController () {

        columnChoices.put("all", "All");
        columnChoices.put("employer", "Employer");
        columnChoices.put("skill", "Skill");

    }

    @RequestMapping("")
    public String list(final Model model) {

        return "list";
    }

    @RequestMapping(value = "jobs")
    public String listJobsByColumnAndValue(final Model model, @RequestParam final String column, @RequestParam final String value) {
        final Iterable<Job> jobs;
        if (column.toLowerCase().equals("all")){
            jobs = jobRepository.findAll();
            model.addAttribute("title", "All Jobs");
        } else {
            jobs = JobData.findByColumnAndValue(column, value, jobRepository.findAll());
            model.addAttribute("title", "Jobs with " + columnChoices.get(column) + ": " + value);
        }
        model.addAttribute("jobs", jobs);

        return "list-jobs";
    }
}
