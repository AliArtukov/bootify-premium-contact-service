package io.bootify.bootify_premium_contact_service.contact;

import io.bootify.bootify_premium_contact_service.security.UserRoles;
import io.bootify.bootify_premium_contact_service.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/contacts")
@PreAuthorize("hasAuthority('" + UserRoles.ROLE_ADMIN + "')")
public class ContactController {

    private final ContactService contactService;

    public ContactController(final ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public String list(@RequestParam(name = "filter", required = false) final String filter,
            @SortDefault(sort = "id") @PageableDefault(size = 20) final Pageable pageable,
            final Model model) {
        final Page<ContactDTO> contacts = contactService.findAll(filter, pageable);
        model.addAttribute("contacts", contacts);
        model.addAttribute("filter", filter);
        model.addAttribute("paginationModel", WebUtils.getPaginationModel(contacts));
        return "contact/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("contact") final ContactDTO contactDTO) {
        return "contact/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("contact") @Valid final ContactDTO contactDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "contact/add";
        }
        contactService.create(contactDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("contact.create.success"));
        return "redirect:/contacts";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("contact", contactService.get(id));
        return "contact/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("contact") @Valid final ContactDTO contactDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "contact/edit";
        }
        contactService.update(id, contactDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("contact.update.success"));
        return "redirect:/contacts";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        contactService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("contact.delete.success"));
        return "redirect:/contacts";
    }

}
