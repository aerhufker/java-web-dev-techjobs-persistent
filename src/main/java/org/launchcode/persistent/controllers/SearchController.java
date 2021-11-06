package org.launchcode.persistent.controllers;
import org.launchcode.persistent.models.Job;
import org.launchcode.persistent.models.JobData;
import org.launchcode.persistent.models.data.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


//import static org.launchcode.techjobs.persistent.controllers.ListController.columnChoices;


/**
 * Created by LaunchCode
 */
@SuppressWarnings("ALL")
@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    private JobRepository jobRepository;

    @RequestMapping("")
    public String search(final Model model) {
        model.addAttribute("columns", ListController.columnChoices);
        return "search";
    }

    @PostMapping("results")
    public String displaySearchResults(final Model model, @RequestParam final String searchType, @RequestParam final String searchTerm){
        final Iterable<Job> jobs;
        if (searchTerm.toLowerCase().equals("all") || searchTerm.equals("")){
            jobs = jobRepository.findAll();
        } else {
            jobs = JobData.findByColumnAndValue(searchType, searchTerm, jobRepository.findAll());
        }
        model.addAttribute("columns", ListController.columnChoices);
        model.addAttribute("title", "Jobs with " + ListController.columnChoices.get(searchType) + ": " + searchTerm);
        model.addAttribute("jobs", jobs);

        return "search";
    }
}