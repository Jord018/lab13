package com.example.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.entity.Organizer;
import com.example.demo.service.OrganizerService;
import com.example.demo.util.LabMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrganizerController {
    final OrganizerService organizerService;

    @GetMapping("/organizers")
    ResponseEntity<?> getOrganizers(@RequestParam(value = "_limit", required = false) Integer perPage,
            @RequestParam(value = "_page", required = false) Integer page) {
        // no paging params: return all (used by the organizer dropdown in the event form)
        if (perPage == null || page == null) {
            return ResponseEntity.ok(LabMapper.INSTANCE.getOrganizerDTO(organizerService.getAllOrganizer()));
        }
        Page<Organizer> pageOutput = organizerService.getOrganizer(page - 1, perPage);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("x-total-count", String.valueOf(pageOutput.getTotalElements()));
        return ResponseEntity.ok().headers(responseHeaders)
                .body(LabMapper.INSTANCE.getOrganizerDTO(pageOutput.getContent()));
    }

    @GetMapping("/organizers/{id}")
    ResponseEntity<?> getOrganizer(@PathVariable("id") Long id) {
        Organizer output = organizerService.getOrganizer(id);
        if (output == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given id is not found");
        }
        return ResponseEntity.ok(LabMapper.INSTANCE.getOrganizerDTO(output));
    }

    @PostMapping("/organizers")
    ResponseEntity<?> addOrganizer(@RequestBody Organizer organizer) {
        Organizer output = organizerService.save(organizer);
        return ResponseEntity.ok(LabMapper.INSTANCE.getOrganizerDTO(output));
    }
}
