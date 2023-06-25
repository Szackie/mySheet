package form.szackie;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
//chcę, żeby Solver zwracał cały output, czyli:
//    1. listę wykonanych detali (List<Form>)
//    2. listę pozostałych po cięciu ścinek (List<Form>)
    //3. no i może jeszcze listę detali, które miały być wykonane (dla porównania, ile się uda zrobić)

    //potem poprzemienia się to na JSONy i wyśle do frontedu

    //a przyjmować ma cały input, czyli:
    // 1. Listę dostępnych ścinek(List<Form>)
    // 2. Listę elementów do wykonania(List<Form>)

    // ( dostanie to z JSONa(też z frontendu(część z excela, druga część wpisywana w okienko(może w formacie:
    // , i chcę, żeby po każdym dodaniu serii elementów do wykonania, wyświetlała się
    // dana formatka w formacie: *width* x *depth* liczba sztuk: *ilość* z możliwością edycji bądź usunięcia)))
//ŚCINKI

    @PostMapping("/scinka")
    ResponseEntity<List<Form>> addNewWaste(@RequestBody Map<String, String> wasteText) {
        String str = wasteText.get("name");
        service=new Service(availableForms,formsToCreate);
        List<Form> newWastes = service.tokenizer(str, 2);
        availableForms.addAll(newWastes);
        return ResponseEntity.ok(availableForms);
    }

    @DeleteMapping("/scinka/{id}")
    ResponseEntity<List<Form>> removeWaste(@PathVariable int id) {
        if (formsToCreate == null)
            return ResponseEntity.notFound().build();
        for (int i = 0; i < availableForms.size(); i++) {
            if (availableForms.get(i).getId() == id) {
                availableForms.remove(i);
                break;
            }
        }
        return ResponseEntity.ok(availableForms);
    }

//FORMATKI


    @PostMapping("/formatka")
    ResponseEntity<List<Form>> addNewForm(@RequestBody Map<String, String> formText) {
        String str = formText.get("name");
        service=new Service(availableForms,formsToCreate);
        List<Form> newForms = service.tokenizer(str, 1);
        formsToCreate.addAll(newForms);
        return ResponseEntity.ok(formsToCreate);
    }

    @DeleteMapping("/formatka/{id}")
    ResponseEntity<List<Form>> removeForm(@PathVariable int id) {
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

    public ResponseEntity<String> solution(){
        service=new Service(availableForms,formsToCreate);
        var result = service.solve();

        return ResponseEntity.ok(result);    //to się sfetchuje i wyśle do frontendu. dodatkowo może jakiś raport do pobrania, czy coś? albo może nie.
    }
/*
    @GetMapping// to musi być kiedy chcę wyświetlić listę ???? przemyśl
    ResponseEntity<List<Product>> findAllProducts() {
        logger.info("Request sended");
        return ResponseEntity.ok(productRepository.findAll());
    }
    @GetMapping// tu inna metoda chyba?
 //    @ResponseBody   ---potrzebne to?
    public ResponseEntity<Service> solution(@RequestBody Map<String, List<Form>> formMap){
        List<Form> availableForms = formMap.get("availableForms");
        List<Form> formsToCreate = formMap.get("formsToCreate");

        service=new Service(availableForms,formsToCreate);
        var result = service.solve();

        return ResponseEntity.ok(result);    //to się sfetchuje i wyśle do frontendu. dodatkowo może jakiś raport do pobrania, czy coś? albo może nie.
    }

*/

}
