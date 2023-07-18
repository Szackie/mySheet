package form.szackie;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class Servlet {

    private Map<String, List<Form>> availableFormsMap = new HashMap<>();
    private Map<String, List<Form>> formsToCreateMap = new HashMap<>();
    private Map<String, Service> serviceMap = new HashMap<>();

    // ŚCINKI

    @PostMapping("/scinka")
    ResponseEntity<List<Form>> addNewWaste(@RequestHeader("userId") String userId, @RequestBody Map<String, String> wasteText) {
        List<Form> availableForms = availableFormsMap.getOrDefault(userId, new ArrayList<>());

        String str = wasteText.get("name");
        List<Form> newWastes = Service.tokenizer(str, 2);
        availableForms.addAll(newWastes);

        availableFormsMap.put(userId, availableForms);

        return ResponseEntity.ok(availableForms);
    }

    @DeleteMapping("/scinka/{id}")
    ResponseEntity<List<Form>> removeWaste(@RequestHeader("userId") String userId, @PathVariable int id) {
        List<Form> availableForms = availableFormsMap.get(userId);
        if (availableForms == null)
            return ResponseEntity.notFound().build();

        availableForms = availableForms.stream().filter(form -> form.getId() != id).collect(Collectors.toList());
        availableFormsMap.put(userId, availableForms);

        return ResponseEntity.ok(availableForms);
    }

    // FORMATKI

    @PostMapping("/formatka")
    ResponseEntity<List<Form>> addNewForm(@RequestHeader("userId") String userId, @RequestBody Map<String, String> formText) {
        List<Form> formsToCreate = formsToCreateMap.getOrDefault(userId, new ArrayList<>());

        String str = formText.get("name");
        List<Form> newForms = Service.tokenizer(str, 1);
        formsToCreate.addAll(newForms);

        formsToCreateMap.put(userId, formsToCreate);

        return ResponseEntity.ok(formsToCreate);
    }

    @DeleteMapping("/formatka/{id}")
    ResponseEntity<List<Form>> removeForm(@RequestHeader("userId") String userId, @PathVariable int id) {
        List<Form> formsToCreate = formsToCreateMap.get(userId);
        if (formsToCreate == null)
            return ResponseEntity.notFound().build();

        formsToCreate = formsToCreate.stream().filter(form -> form.getId() != id).collect(Collectors.toList());
        formsToCreateMap.put(userId, formsToCreate);

        return ResponseEntity.ok(formsToCreate);
    }

    @GetMapping("/solve")
    public ResponseEntity<Service> solution(@RequestHeader("userId") String userId) {
        List<Form> availableForms = availableFormsMap.getOrDefault(userId, new ArrayList<>());
        List<Form> formsToCreate = formsToCreateMap.getOrDefault(userId, new ArrayList<>());

        Service service = new Service(availableForms, formsToCreate);
        serviceMap.put(userId, service);

        var result = service.solve();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/reset")
    public ResponseEntity<?> removeAllData(@RequestHeader("userId") String userId) {
        availableFormsMap.remove(userId);
        formsToCreateMap.remove(userId);
        serviceMap.remove(userId);

        return ResponseEntity.ok().build();
    }
}
