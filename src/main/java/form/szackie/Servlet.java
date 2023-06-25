package form.szackie;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/formatka")
public class Servlet {

    Service service;
    List<Form> availableForms = new ArrayList<>();
    List<Form> formsToCreate = new ArrayList<>();

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

    @PostMapping
    ResponseEntity <List<Form>> addNewForm(@RequestBody Map<String, String> formText) {
        String str = (String) formText.get("name");
        Form newForm = new Form(str);
        System.out.println(newForm.toString());
        formsToCreate.add(newForm);
        return ResponseEntity.ok(formsToCreate);
    }

    @DeleteMapping("/{id}")
        // PONIŻEJ MOŻE BYĆ INTEGER, ZAMIAST INT
    ResponseEntity<List<Form>> removeForm(@PathVariable int id){
        formsToCreate.remove(id-1);
        return ResponseEntity.ok(formsToCreate);
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
