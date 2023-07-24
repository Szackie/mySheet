package form.szackie;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@SuppressWarnings("unused")
public class Servlet {

    private final Solution solution = new Solution();


    private Service service;

//ŚCINKI
@SuppressWarnings("unused")
    @PostMapping("/scinka")
    ResponseEntity<List<Form>> addNewWaste(@RequestBody Map<String, String> wasteText) {
        String str = wasteText.get("name");
        List<Form> newWastes = Service.tokenizer(str, 2);
        solution.getNewWastesList().addAll(newWastes);
        return ResponseEntity.ok(solution.getNewWastesList());
    }
    @SuppressWarnings("unused")
    @DeleteMapping("/scinka/{id}")
    ResponseEntity<List<Form>> removeWaste(@PathVariable int id) {
        for (int i = 0; i < solution.getNewWastesList().size(); i++) {
            if (solution.getNewWastesList().get(i).getId() == id) {
                solution.getNewWastesList().remove(i);
                break;
            }
        }
        return ResponseEntity.ok(solution.getNewWastesList());
    }

//FORMATKI
@SuppressWarnings("unused")
    @PostMapping("/formatka")
    ResponseEntity<List<Form>> addNewForm(@RequestBody Map<String, String> formText) {
        String str = formText.get("name");
        List<Form> newForms = Service.tokenizer(str, 1);
        this.solution.getNewFormsList().addAll(newForms);
        return ResponseEntity.ok(this.solution.getNewFormsList());
    }
    @SuppressWarnings("unused")
    @DeleteMapping("/formatka/{id}")
    ResponseEntity<List<Form>> removeForm(@PathVariable int id) {
        for (int i = 0; i < solution.getNewFormsList().size(); i++) {
            if (solution.getNewFormsList().get(i).getId() == id) {
                solution.getNewFormsList().remove(i);
                break;
            }
        }
        return ResponseEntity.ok(solution.getNewFormsList());
    }

    @GetMapping("/solve")
    @SuppressWarnings("unused")
    public ResponseEntity<Solution> solution() {
        service = new Service(solution.getNewWastesList(), solution.getNewFormsList());

        var result = service.solve();

        return ResponseEntity.ok(result);
    }
    @SuppressWarnings("unused")
    @DeleteMapping("/reset")
    public ResponseEntity<?> removeAllData() {
        if (solution.getNewFormsList() != null)
            solution.getNewFormsList().clear();
        if (solution.getNewWastesList() != null)
            solution.getNewWastesList().clear();
        if (service != null)
            service.reset();

        return ResponseEntity.ok().build();
    }

}
