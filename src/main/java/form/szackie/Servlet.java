package form.szackie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    private final HttpSession httpSession;

    @Autowired
    public Servlet(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @PostMapping("/scinka")
    public ResponseEntity<List<Form>> addNewWaste(@RequestBody Map<String, String> wasteText) {
        String str = wasteText.get("name");
        List<Form> newWastes = Service.tokenizer(str, 2);
        availableForms.addAll(newWastes);
        return ResponseEntity.ok(availableForms);
    }

    @DeleteMapping("/scinka/{id}")
    public ResponseEntity<List<Form>> removeWaste(@PathVariable int id) {
        if (availableForms == null)
            return ResponseEntity.notFound().build();
        for (int i = 0; i < availableForms.size(); i++) {
            if (availableForms.get(i).getId() == id) {
                availableForms.remove(i);
                break;
            }
        }
        return ResponseEntity.ok(availableForms);
    }

    @PostMapping("/formatka")
    public ResponseEntity<List<Form>> addNewForm(@RequestBody Map<String, String> formText) {
        String str = formText.get("name");
        List<Form> newForms = Service.tokenizer(str, 1);
        formsToCreate.addAll(newForms);
        return ResponseEntity.ok(formsToCreate);
    }

    @DeleteMapping("/formatka/{id}")
    public ResponseEntity<List<Form>> removeForm(@PathVariable int id) {
        if (formsToCreate == null)
            return ResponseEntity.notFound().build();
        for (int i = 0; i < formsToCreate.size(); i++) {
            if (formsToCreate.get(i).getId() == id) {
                formsToCreate.remove(i);
                break;
            }
        }
        return ResponseEntity.ok(formsToCreate);
    }

    @GetMapping("/solve")
    public ResponseEntity<Service> solution() {
        service = new Service(availableForms, formsToCreate);
        var result = service.solve();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/reset")
    public ResponseEntity<?> removeAllData() {
        if (formsToCreate != null)
            formsToCreate.clear();
        if (availableForms != null)
            availableForms.clear();
        if (service != null)
            service.reset();

        return ResponseEntity.ok().build();
    }
}
