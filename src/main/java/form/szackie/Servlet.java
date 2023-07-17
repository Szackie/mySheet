package form.szackie;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@SessionAttributes({"availableForms", "formsToCreate"})
public class Servlet {

    @ModelAttribute("availableForms")
    public List<Form> availableForms() {
        return new ArrayList<>();
    }

    @ModelAttribute("formsToCreate")
    public List<Form> formsToCreate() {
        return new ArrayList<>();
    }

    Service service;

    @PostMapping("/scinka")
    ResponseEntity<List<Form>> addNewWaste(@RequestBody Map<String, String> wasteText, @ModelAttribute("availableForms") List<Form> availableForms) {
        String str = wasteText.get("name");
        List<Form> newWastes = Service.tokenizer(str, 2);
        availableForms.addAll(newWastes);
        return ResponseEntity.ok(availableForms);
    }

    @DeleteMapping("/scinka/{id}")
    ResponseEntity<List<Form>> removeWaste(@PathVariable int id, @ModelAttribute("availableForms") List<Form> availableForms) {
        availableForms = availableForms.stream()
                .filter(form -> form.getId() != id)
                .collect(Collectors.toList());
        return ResponseEntity.ok(availableForms);
    }

    @PostMapping("/formatka")
    ResponseEntity<List<Form>> addNewForm(@RequestBody Map<String, String> formText, @ModelAttribute("formsToCreate") List<Form> formsToCreate) {
        String str = formText.get("name");
        List<Form> newForms = Service.tokenizer(str, 1);
        formsToCreate.addAll(newForms);
        return ResponseEntity.ok(formsToCreate);
    }

    @DeleteMapping("/formatka/{id}")
    ResponseEntity<List<Form>> removeForm(@PathVariable int id, @ModelAttribute("formsToCreate") List<Form> formsToCreate) {
        formsToCreate = formsToCreate.stream()
                .filter(form -> form.getId() != id)
                .collect(Collectors.toList());
        return ResponseEntity.ok(formsToCreate);
    }

    @GetMapping("/solve")
    public ResponseEntity<Service> solution(@ModelAttribute("availableForms") List<Form> availableForms, @ModelAttribute("formsToCreate") List<Form> formsToCreate) {
        service = new Service(availableForms, formsToCreate);
        var result = service.solve();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/reset")
    public ResponseEntity<?> removeAllData(SessionStatus sessionStatus) {
        sessionStatus.setComplete();
        return ResponseEntity.ok().build();
    }
}
