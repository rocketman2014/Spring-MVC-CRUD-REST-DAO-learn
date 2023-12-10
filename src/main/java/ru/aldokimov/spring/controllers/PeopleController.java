package ru.aldokimov.spring.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.aldokimov.spring.dao.PersonDAO;
import ru.aldokimov.spring.models.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    //можно и не писать, лучше использовать конструктор чем просто @Aotowired над полем!!!(по конвенции)
    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }


    @GetMapping()
    public String index(Model model) {
        //Получим всех людей из дао и педадим на отображение в представлении
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        //Получим 1 человека по егго id из дао и передадим на отображение в представлении
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/people/new";
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "people/edit";
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
