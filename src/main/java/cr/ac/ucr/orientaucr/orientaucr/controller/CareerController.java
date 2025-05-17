/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Controller.java to edit this template
 */
package cr.ac.ucr.orientaucr.orientaucr.controller;

import cr.ac.ucr.orientaucr.orientaucr.domain.Career;
import cr.ac.ucr.orientaucr.orientaucr.service.CareerService;
import java.util.LinkedList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/Career")
public class CareerController {

    @GetMapping("/listCareers")
    public String page(Model model) {
        model.addAttribute("career", CareerService.getAllCareers());
        return "careers/listCareers";
    }

    // Nuevo endpoint para API REST
    @RequestMapping("/api/list")
    @ResponseBody
    public ResponseEntity<LinkedList<Career>> getAllCareers() {
        return ResponseEntity.ok(CareerService.getAllCareers());
    }

    @PostMapping("/api/add")
    public ResponseEntity<Void> addCareer(@RequestBody Career career) {
        CareerService.addCareer(career);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
