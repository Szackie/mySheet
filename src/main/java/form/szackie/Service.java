package form.szackie;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {// OBSERVERA DODAJ!!! AKTUALIZUJ LISTY
    private List<Form> availableForms;
    private List<Form> desiredForms;

    /**
     * Check if it possible to create new form from existing form. If its not, returns null
     *
     * @param existingForm
     * @param formToCreate
     * @return two new available forms from waste(achtung! depth and with could be less than zero!)
     */
    public List<Form> fitsInForm(Form existingForm, Form formToCreate) {
        Form firstWaste;
        Form secondWaste;
        List<Form> list = new ArrayList<>();

        int currDepth = existingForm.getDepth();
        int dessDepth = formToCreate.getDepth();
        int currWidth = existingForm.getWidth();
        int dessWidth = formToCreate.getWidth();

        if (currDepth >= dessDepth && currWidth >= dessWidth) { //sprawdzam, czy się zmieści
            if ((((currWidth + 6) % (dessWidth + 6)) * ((currDepth + 6) % (dessDepth + 6))) >= (((currWidth + 6) % (dessDepth + 6)) * ((currDepth + 6) % (dessWidth + 6))))//jeśli jest opcja ułożenia inaczej, to ma ułożyć tak, żeby zostało jaknajmniej odpadu
            {
                firstWaste = new Form(currWidth - dessDepth - 6, currDepth);  //MOGĄ WYCHODZIĆ TU UJEMNE(OGARNIJ)

                secondWaste = new Form(dessDepth, currDepth - dessWidth - 6); //MOGĄ WYCHODZIĆ TU UJEMNE(OGARNIJ)
                list.add(firstWaste);
                list.add(secondWaste);
                return list;
            }
            firstWaste = new Form(currWidth - dessWidth - 6, currDepth);  //dobrze??????????
            secondWaste = new Form(dessWidth, currDepth - dessDepth - 6);//dobrze??????????
            list.add(firstWaste);
            list.add(secondWaste);
            return list;
        }

        return null;
    }

    public Form sort(Form formatka) {
        if (formatka.getWidth() > formatka.getDepth())
            return new Form(formatka.getDepth(), formatka.getWidth(), formatka.getQuantity());

        return formatka;
    }

    public String solve() {
        String result = "Możesz wykonać ";

        //sort() --sortuj wg powierzchi(żeby sprawdzało najpierw największe detale od najmniejszych ścinek)
        //ustaw szerokości -- tak żeby zawsze krótszy bok był szerokością!!
        for (int z = 0; z < availableForms.size(); z++)
            availableForms.set(z, sort(availableForms.get(z)));
        for (int z = 0; z < desiredForms.size(); z++)
            desiredForms.set(z, sort(desiredForms.get(z)));


        for (int j = 0; j < desiredForms.size(); j++) {
            int i = 0;
            int quantity = 0;
            while (i < availableForms.size() && quantity < desiredForms.get(j).getQuantity()) {
                List<Form> temp = fitsInForm(availableForms.get(i), desiredForms.get(j));
                if (temp != null) {
                    availableForms.remove(i);
                    i--;
                    quantity++;

                    for (int k = 0; k < 2; k++) {
                        if (temp.get(k).getWidth() >= 6 && temp.get(k).getDepth() >= 6)
                            availableForms.add(temp.get(k));
                    }
                }


                i++;
            }
            result += "\ndetal: " + desiredForms.get(j).toString() + ", ilość: " + quantity;
        }
//        while (index < dlugosc listy desiredForms)
//
//        selectSuitableForms(desiredForm1);
//        zoptymalizuj(jak lepiej to wycinać, żeby sie wiecej zmiescilo)(osobna metoda?)
//        potnij je (setCutSuitableForms() oraz set desiredForms(masz index wiec dodawaj tylko liczbe przez przejscia);)
//        cutSuitableForms dodaj do availableForms
//    }
        result += "\n Pozostałe ścinki: ";
        for (int i = 0; i < availableForms.size(); i++) {
            result += "\n " + availableForms.get(i).getWidth() + " " + availableForms.get(i).getDepth();
        }
        return result;
    }


    public Service(List<Form> availableForms, List<Form> desiredForms) {
        this.availableForms = availableForms;
        this.desiredForms = desiredForms;
    }

    public List<Form> getAvailableForms() {
        return availableForms;
    }

    public void setAvailableForms(List<Form> availableForms) {
        this.availableForms = availableForms;
    }

    public List<Form> getDesiredForms() {
        return desiredForms;
    }

    public void setDesiredForms(List<Form> desiredForms) {
        this.desiredForms = desiredForms;
    }

    public List<Form> tokenizer(String text, int type) {

        Pattern pattern = Pattern.compile("-?\\d+"); // Wzorzec dla liczby całkowitej(MOżE być ujemna!!!)
        Matcher matcher = pattern.matcher(text);
        List<Form> list = new ArrayList<>();


        int width;
        int depth;
        int quantity;

        if (type == 1) {
            while (matcher.find()) {
                width=0;
                depth=0;
                quantity=0;

                width = Integer.parseInt(matcher.group());

                if (matcher.find()) {
                    depth = Integer.parseInt(matcher.group());
                }
                if (matcher.find()) {
                    quantity = Integer.parseInt(matcher.group());
                }
                list.add(new Form(width, depth, quantity));
            }
        } else {
            while (matcher.find()) {
                width=0;
                depth=0;
                quantity=0;
                width = Integer.parseInt(matcher.group());

                if (matcher.find()) {
                    depth = Integer.parseInt(matcher.group());
                    quantity = 1;
                }
                list.add(new Form(width, depth, quantity));
            }

        }
        return list;
    }
}
