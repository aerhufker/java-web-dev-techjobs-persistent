
package org.launchcode.persistent.controllers;

import org.launchcode.persistent.models.Employer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@SuppressWarnings("ALL")
@Controller
@RequestMapping("employers")
public class EmployerController {


    public EmployerController() {
    }

    @GetMapping("add")
    public String displayAddEmployerForm(final Model model) {
        model.addAttribute(new Employer());
        return "employers/add";
    }

    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid final Employer newEmployer,
                                         final Errors errors, final Model model) {

        if (errors.hasErrors()) {
            return "employers/add";
        }

        return "redirect:";
    }

    @GetMapping("view/{employerId}")
    public String displayViewEmployer(final Model model, @PathVariable final int employerId) {

        final Optional optEmployer = null;
        if (optEmployer.isPresent()) {
            final Employer employer = (Employer) optEmployer.get();
            model.addAttribute("employer", employer);
            return "employers/view";
        } else {
            return "redirect:../";
        }
    }
}
