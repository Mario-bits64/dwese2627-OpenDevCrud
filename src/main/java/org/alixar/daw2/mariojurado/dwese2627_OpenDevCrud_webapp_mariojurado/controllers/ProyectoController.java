package org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.controllers;

import jakarta.validation.Valid;
import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.dtos.*;
import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.exceptions.DuplicateResourceException;
import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.exceptions.ResourceNotFoundException;
import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.services.ProyectoService;
import org.alixar.daw2.mariojurado.dwese2627_OpenDevCrud_webapp_mariojurado.services.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

/**
 * Controlador del CRUD de proyectos.
 *
 * @author Mario
 * @version 1.0
 */
@Controller
@RequestMapping("/proyectos")
public class ProyectoController {

    private static final Logger logger = LoggerFactory.getLogger(ProyectoController.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Muestra el listado de proyectos paginado y ordenado.
     */
    @GetMapping
    public String listProyectos(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "nombre") String sortField,
                                @RequestParam(defaultValue = "asc") String sortDir,
                                Model model, Locale locale) {

        logger.info("Listando proyectos page={}, size={}, sortField={}, sortDir={}", page, size, sortField, sortDir);

        try {
            if (size <= 0) size = 10;

            long totalElements = proyectoService.count();
            int totalPages = (int) Math.ceil((double) totalElements / size);
            if (page < 0) page = 0;
            if (totalPages > 0 && page >= totalPages) page = totalPages - 1;

            List<ProyectoDTO> proyectos = proyectoService.listPage(page, size, sortField, sortDir);

            model.addAttribute("proyectos", proyectos);
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("totalElements", totalElements);
            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDir", sortDir);
            model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        } catch (Exception e) {
            logger.error("Error al listar los proyectos: {}", e.getMessage(), e);
            model.addAttribute("errorMessage",
                    messageSource.getMessage("msg.proyecto-controller.list.error", null, locale));
        }
        return "views/proyecto/proyecto-list";
    }

    /**
     * Muestra el detalle de un proyecto con los datos de su usuario.
     */
    @GetMapping("/detail")
    public String showDetail(@RequestParam("id") Long id,
                             Model model,
                             RedirectAttributes redirectAttributes,
                             Locale locale) {

        logger.info("Mostrando detalle del proyecto con ID {}", id);

        try {
            ProyectoDetailDTO proyectoDTO = proyectoService.getDetail(id);
            model.addAttribute("proyecto", proyectoDTO);
            return "views/proyecto/proyecto-detail";

        } catch (ResourceNotFoundException ex) {
            logger.warn("No se encontró el proyecto con ID {}", id);
            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage("msg.proyecto-controller.detail.notFound", null, locale));
            return "redirect:/proyectos";

        } catch (Exception e) {
            logger.error("Error al obtener el detalle del proyecto {}: {}", id, e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage("msg.proyecto-controller.detail.error", null, locale));
            return "redirect:/proyectos";
        }
    }

    /**
     * Muestra el formulario para crear un proyecto.
     */
    @GetMapping("/new")
    public String showNewForm(Model model) {
        logger.info("Mostrando formulario para nuevo proyecto.");
        model.addAttribute("proyecto", new ProyectoCreateDTO());
        model.addAttribute("usuarios", usuarioService.listAll());
        return "views/proyecto/proyecto-form";
    }

    /**
     * Muestra el formulario para editar un proyecto.
     */
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id,
                               Model model,
                               RedirectAttributes redirectAttributes,
                               Locale locale) {

        logger.info("Mostrando formulario de edición para el proyecto con ID {}", id);

        try {
            ProyectoUpdateDTO proyectoDTO = proyectoService.getForEdit(id);
            model.addAttribute("proyecto", proyectoDTO);
            model.addAttribute("usuarios", usuarioService.listAll());
            return "views/proyecto/proyecto-form";

        } catch (ResourceNotFoundException ex) {
            logger.warn("No se encontró el proyecto con ID {}", id);
            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage("msg.proyecto-controller.detail.notFound", null, locale));
            return "redirect:/proyectos";

        } catch (Exception e) {
            logger.error("Error al obtener el proyecto con ID {}: {}", id, e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage("msg.proyecto-controller.edit.error", null, locale));
            return "redirect:/proyectos";
        }
    }

    /**
     * Inserta un proyecto nuevo.
     */
    @PostMapping("/insert")
    public String insertProyecto(@Valid @ModelAttribute("proyecto") ProyectoCreateDTO proyectoDTO,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes,
                                 Locale locale) {

        logger.info("Insertando nuevo proyecto con nombre {}", proyectoDTO.getNombre());

        try {
            validarFechaFinalizacion(proyectoDTO.getFase(), proyectoDTO.getFechaFinalizacion(), result);

            if (result.hasErrors()) {
                model.addAttribute("usuarios", usuarioService.listAll());
                return "views/proyecto/proyecto-form";
            }

            proyectoService.create(proyectoDTO);

            redirectAttributes.addFlashAttribute("successMessage",
                    messageSource.getMessage("msg.proyecto-controller.insert.success", null, locale));
            return "redirect:/proyectos";

        } catch (DuplicateResourceException ex) {
            logger.warn("El nombre del proyecto {} ya existe.", proyectoDTO.getNombre());
            result.rejectValue("nombre", "msg.proyecto-controller.insert.nombreExist");
            model.addAttribute("usuarios", usuarioService.listAll());
            return "views/proyecto/proyecto-form";

        } catch (ResourceNotFoundException ex) {
            logger.warn("No se encontró el usuario con ID {}", proyectoDTO.getUsuarioId());
            result.rejectValue("usuarioId", "msg.proyecto-controller.usuario.notFound");
            model.addAttribute("usuarios", usuarioService.listAll());
            return "views/proyecto/proyecto-form";

        } catch (Exception e) {
            logger.error("Error al insertar el proyecto {}: {}", proyectoDTO.getNombre(), e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage("msg.proyecto-controller.insert.error", null, locale));
            return "redirect:/proyectos/new";
        }
    }

    /**
     * Actualiza un proyecto existente.
     */
    @PostMapping("/update")
    public String updateProyecto(@Valid @ModelAttribute("proyecto") ProyectoUpdateDTO proyectoDTO,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes,
                                 Locale locale) {

        logger.info("Actualizando proyecto con ID {}", proyectoDTO.getId());

        try {
            validarFechaFinalizacion(proyectoDTO.getFase(), proyectoDTO.getFechaFinalizacion(), result);

            if (result.hasErrors()) {
                model.addAttribute("usuarios", usuarioService.listAll());
                return "views/proyecto/proyecto-form";
            }

            proyectoService.update(proyectoDTO);

            redirectAttributes.addFlashAttribute("successMessage",
                    messageSource.getMessage("msg.proyecto-controller.update.success", null, locale));
            return "redirect:/proyectos";

        } catch (DuplicateResourceException ex) {
            logger.warn("El nombre del proyecto {} ya existe para otro proyecto.", proyectoDTO.getNombre());
            result.rejectValue("nombre", "msg.proyecto-controller.update.nombreExist");
            model.addAttribute("usuarios", usuarioService.listAll());
            return "views/proyecto/proyecto-form";

        } catch (ResourceNotFoundException ex) {
            if ("usuario".equals(ex.getResource())) {
                logger.warn("No se encontró el usuario con ID {}", proyectoDTO.getUsuarioId());
                result.rejectValue("usuarioId", "msg.proyecto-controller.usuario.notFound");
                model.addAttribute("usuarios", usuarioService.listAll());
                return "views/proyecto/proyecto-form";
            }
            logger.warn("No se encontró el proyecto con ID {}", proyectoDTO.getId());
            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage("msg.proyecto-controller.detail.notFound", null, locale));
            return "redirect:/proyectos";

        } catch (Exception e) {
            logger.error("Error al actualizar el proyecto con ID {}: {}", proyectoDTO.getId(), e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage("msg.proyecto-controller.update.error", null, locale));
            return "redirect:/proyectos/edit?id=" + proyectoDTO.getId();
        }
    }

    /**
     * Elimina un proyecto.
     */
    @PostMapping("/delete")
    public String deleteProyecto(@RequestParam("id") Long id,
                                 RedirectAttributes redirectAttributes,
                                 Locale locale) {

        logger.info("Eliminando proyecto con ID {}", id);

        try {
            proyectoService.delete(id);

            redirectAttributes.addFlashAttribute("successMessage",
                    messageSource.getMessage("msg.proyecto-controller.delete.success", null, locale));
            return "redirect:/proyectos";

        } catch (ResourceNotFoundException ex) {
            logger.warn("No se encontró el proyecto con ID {}", id);
            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage("msg.proyecto-controller.detail.notFound", null, locale));
            return "redirect:/proyectos";

        } catch (Exception e) {
            logger.error("Error al eliminar el proyecto con ID {}: {}", id, e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage("msg.proyecto-controller.delete.error", null, locale));
            return "redirect:/proyectos";
        }
    }

    /**
     * Vuelve al formulario si se cambia de idioma después de un error al crear o editar.
     */
    @GetMapping({"/insert", "/update"})
    public String redirectToForm(@RequestParam(value = "id", required = false) Long id) {
        return (id == null) ? "redirect:/proyectos/new" : "redirect:/proyectos/edit?id=" + id;
    }

    /**
     * Comprueba la fecha de finalización: obligatoria si el proyecto está finalizado
     * y no puede ser posterior a la fecha actual.
     */
    private void validarFechaFinalizacion(String fase, LocalDateTime fechaFinalizacion, BindingResult result) {
        if (result.hasFieldErrors("fechaFinalizacion")) {
            return;
        }
        if ("FINALIZADO".equals(fase) && fechaFinalizacion == null) {
            result.rejectValue("fechaFinalizacion", "msg.proyecto.fechaFinalizacion.required");
        } else if (fechaFinalizacion != null && fechaFinalizacion.isAfter(LocalDateTime.now())) {
            result.rejectValue("fechaFinalizacion", "msg.proyecto.fechaFinalizacion.future");
        }
    }
}
