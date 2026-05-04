package com.miniProject.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AppErrorController {

    @RequestMapping("/app-error")
    public String handleError(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null && "413".equals(status.toString())) {
            redirectAttributes.addFlashAttribute("uploadError", "Ảnh vượt quá dung lượng cho phép (5MB).");
            String referer = request.getHeader("Referer");
            if (referer != null && !referer.isBlank()) {
                return "redirect:" + referer;
            }
            return "redirect:/admin/products/new";
        }
        return "redirect:/";
    }
}
