//package form.szackie;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class ServiceTests {
//    @Test
//    public void testSolve() {
//        List<Form> availableForms = Arrays.asList(new Form(1500, 3000, 1), new Form(100, 100, 1));
//        List<Form> formsToCreate = Arrays.asList(new Form(1400, 1500, 1), new Form(94, 3000, 1));
//        List<Form> expectedWastesList = Arrays.asList(new Form(1500,3000,1));
//        List<Form> expectedFormsList = Arrays.asList(new Form(1400, 1500, 1), new Form(90, 3000, 1));
//
//        Service service = new Service(availableForms, formsToCreate);
//        Service result = service.solve();
//
//        Assertions.assertEquals(expectedWastesList, result.getNewWastesList());
//        Assertions.assertEquals(expectedFormsList, result.getNewFormsList());
//    }
//}
