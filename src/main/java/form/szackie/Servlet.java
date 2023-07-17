package form.szackie;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class Servlet {

    List<Form> availableForms = new ArrayList<>();
    List<Form> formsToCreate = new ArrayList<>();

    Service service;

    @PostMapping("/scinka")
    ResponseEntity<List<Form>> addNewWaste(@RequestBody Map<String, String> wasteText) {
        String str = wasteText.get("name");
        List<Form> newWastes = Service.tokenizer(str, 2);
        availableForms.addAll(newWastes);
        return ResponseEntity.ok(availableForms);
    }

    @DeleteMapping("/scinka/{id}")
    ResponseEntity<List<Form>> removeWaste(@PathVariable int id) {
        availableForms = availableForms.stream()
                .filter(form -> form.getId() != id)
                .collect(Collectors.toList());
        return ResponseEntity.ok(availableForms);
    }

    @PostMapping("/formatka")
    ResponseEntity<List<Form>> addNewForm(@RequestBody Map<String, String> formText) {
        String str = formText.get("name");
        List<Form> newForms = Service.tokenizer(str, 1);
        formsToCreate.addAll(newForms);
        return ResponseEntity.ok(formsToCreate);
    }

    @DeleteMapping("/formatka/{id}")
    ResponseEntity<List<Form>> removeForm(@PathVariable int id) {
        formsToCreate = formsToCreate.stream()
                .filter(form -> form.getId() != id)
                .collect(Collectors.toList());
        return ResponseEntity.ok(formsToCreate);
    }

    @GetMapping("/solve")
    public ResponseEntity<Service> solution() {
        service = new Service(availableForms, formsToCreate);
        var result = service.solve();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/reset")
    public ResponseEntity<?> removeAllData(HttpServletRequest request, HttpServletResponse response) {
        availableForms.clear();
        formsToCreate.clear();
        if (service != null)
            service.reset();

        // Usunięcie plików cookie sesji
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }

        return ResponseEntity.ok().build();
    }
}
