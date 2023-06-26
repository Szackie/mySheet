package form.szackie;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {// OBSERVERA DODAJ!!! AKTUALIZUJ LISTY (?)
    private List<Form> newWastesList;
    private List<Form> newFormsList;

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

    public Service solve() {
        String result = "Możesz wykonać ";

        //sort() --sortuj wg powierzchi(żeby sprawdzało najpierw największe detale od najmniejszych ścinek)
        //ustaw szerokości -- tak żeby zawsze krótszy bok był szerokością!!
        for (int z = 0; z < newWastesList.size(); z++)
            newWastesList.set(z, sort(newWastesList.get(z)));
        for (int z = 0; z < newFormsList.size(); z++)
            newFormsList.set(z, sort(newFormsList.get(z)));


        for (int j = 0; j < newFormsList.size(); j++) {
            int i = 0;
            int quantity = 0;
            while (i < newWastesList.size() && quantity < newFormsList.get(j).getQuantity()) {
                List<Form> temp = fitsInForm(newWastesList.get(i), newFormsList.get(j));
                if (temp != null) {
                    newWastesList.remove(i);
                    i--;
                    quantity++;

                    for (int k = 0; k < 2; k++) {
                        if (temp.get(k).getWidth() >= 6 && temp.get(k).getDepth() >= 6)
                            newWastesList.add(temp.get(k));
                    }
                }


                i++;
            }
            result += "\ndetal: " + newFormsList.get(j).toString() + ", ilość: " + quantity;
            newFormsList.get(j).setQuantity(quantity);
        }
//        while (index < dlugosc listy desiredForms)
//
//        selectSuitableForms(desiredForm1);
//        zoptymalizuj(jak lepiej to wycinać, żeby sie wiecej zmiescilo)(osobna metoda?)
//        potnij je (setCutSuitableForms() oraz set desiredForms(masz index wiec dodawaj tylko liczbe przez przejscia);)
//        cutSuitableForms dodaj do availableForms
//    }
        result += "\n Pozostałe ścinki: ";
        for (int i = 0; i < newWastesList.size(); i++) {
            result += "\n " + newWastesList.get(i).getWidth() + " " + newWastesList.get(i).getDepth();
        }
        System.out.println(result);

        return new Service(newWastesList, newFormsList);
    }


    public Service(List<Form> availableForms, List<Form> desiredForms) {
        this.newWastesList = availableForms;
        this.newFormsList = desiredForms;
    }

    public List<Form> getNewWastesList() {
        return newWastesList;
    }

    public void setNewWastesList(List<Form> newWastesList) {
        this.newWastesList = newWastesList;
    }

    public List<Form> getNewFormsList() {
        return newFormsList;
    }

    public void setNewFormsList(List<Form> newFormsList) {
        this.newFormsList = newFormsList;
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
