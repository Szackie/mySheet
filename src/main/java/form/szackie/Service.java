package form.szackie;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {// OBSERVERA DODAJ!!! AKTUALIZUJ LISTY (?)
    private List<Form> newWastesList;
    private List<Form> newFormsList;



    public List<Form> checkNextPossibility(Form waste, Form form, int startIndex) {
        if (fitsInForm(waste, form) == null)
            return new ArrayList<>(Arrays.asList(new Form(0, 0), new Form(0, 0)));


        int A = waste.getWidth();
        int B = waste.getDepth();
        int a = form.getWidth();
        int b = form.getDepth();

        List<CompareArea> comparator = new ArrayList<>(8);

        for (int i = 0; i < 8; i++) {
            CompareArea c = new CompareArea(form.getArea(), startIndex); // Metoda tworząca i inicjalizująca obiekt
            comparator.add(c);
        }

        int max = 0;

        if (startIndex < newFormsList.size() && fitsBothWays(waste, form)) {
            for (int i = startIndex; i < newFormsList.size(); i++) {

                comparator.get(0).setSumArea(comparator.get(0).getSumArea() + checkNextPossibility(new Form(B, A - a), newFormsList.get(i), i + 1).get(1).getArea()); //
                checkNextPossibility(new Form(B, A - a), newFormsList.get(i), i + 1).get(1).setComparator(comparator.get(0).getSumArea());
                comparator.get(0).setFormIndex(i);

                comparator[1] += checkNextPossibility(new Form(a, B - b), newFormsList.get(i), i + 1).get(1).getComparator() + checkNextPossibility(new Form(a, B - b), newFormsList.get(i), i + 1).get(1).getWidth() * checkNextPossibility(new Form(a, B - b), newFormsList.get(i), i + 1).get(1).getDepth();//
                checkNextPossibility(new Form(a, B - b), newFormsList.get(i), i + 1).get(1).setComparator(comparator[1]);


                comparator[2] += checkNextPossibility(new Form(B - b, A), newFormsList.get(i), i + 1).get(1).getComparator() + checkNextPossibility(new Form(B - b, A), newFormsList.get(i), i + 1).get(1).getWidth() * checkNextPossibility(new Form(B - b, A), newFormsList.get(i), i + 1).get(1).getDepth();//
                checkNextPossibility(new Form(B - b, A), newFormsList.get(i), i + 1).get(1).setComparator(comparator[2]);


                comparator[3] += checkNextPossibility(new Form(A - a, b), newFormsList.get(i), i + 1).get(1).getComparator() + checkNextPossibility(new Form(A - a, b), newFormsList.get(i), i + 1).get(1).getWidth() * checkNextPossibility(new Form(A - a, b), newFormsList.get(i), i + 1).get(1).getDepth();//
                checkNextPossibility(new Form(A - a, b), newFormsList.get(i), i + 1).get(1).setComparator(comparator[3]);


                comparator[4] += checkNextPossibility(new Form(A - b, B), newFormsList.get(i), i + 1).get(1).getComparator() + checkNextPossibility(new Form(A - b, B), newFormsList.get(i), i + 1).get(1).getWidth() * checkNextPossibility(new Form(A - b, B), newFormsList.get(i), i + 1).get(1).getDepth();//
                checkNextPossibility(new Form(A - b, B), newFormsList.get(i), i + 1).get(1).setComparator(comparator[4]);


                comparator[5] += checkNextPossibility(new Form(B - a, b), newFormsList.get(i), i + 1).get(1).getComparator() + checkNextPossibility(new Form(B - a, b), newFormsList.get(i), i + 1).get(1).getWidth() * checkNextPossibility(new Form(B - a, b), newFormsList.get(i), i + 1).get(1).getDepth();//
                checkNextPossibility(new Form(B - a, b), newFormsList.get(i), i + 1).get(1).setComparator(comparator[5]);


                comparator[6] += checkNextPossibility(new Form(B - a, A), newFormsList.get(i), i + 1).get(1).getComparator() + checkNextPossibility(new Form(B - a, A), newFormsList.get(i), i + 1).get(1).getWidth() * checkNextPossibility(new Form(B - a, A), newFormsList.get(i), i + 1).get(1).getDepth();//
                checkNextPossibility(new Form(B - a, A), newFormsList.get(i), i + 1).get(1).setComparator(comparator[6]);


                comparator[7] += checkNextPossibility(new Form(A - b, a), newFormsList.get(i), i + 1).get(1).getComparator() + checkNextPossibility(new Form(A - b, a), newFormsList.get(i), i + 1).get(1).getWidth() * checkNextPossibility(new Form(A - b, a), newFormsList.get(i), i + 1).get(1).getDepth();//
                checkNextPossibility(new Form(A - b, a), newFormsList.get(i), i + 1).get(1).setComparator(comparator[7]);


//                Wcześniejszy pomysł zakładał użycie dodatkowego pola comparator w Form, ale w sumie to nie zależy mi, żeby oznaczać jakoś wszytkie
//                formatki, tylko te, które w tej pętli dadzą maksa
//
//                comparator[1] += checkNextPossibility(new Form(a, B - b), newFormsList.get(i), i + 1).get(1).getComparator() + checkNextPossibility(new Form(a, B - b), newFormsList.get(i), i + 1).get(1).getWidth() * checkNextPossibility(new Form(a, B - b), newFormsList.get(i), i + 1).get(1).getDepth();//
//                checkNextPossibility(new Form(a, B - b), newFormsList.get(i), i + 1).get(1).setComparator(comparator[1]);
//
//
//                comparator[2] += checkNextPossibility(new Form(B - b, A), newFormsList.get(i), i + 1).get(1).getComparator() + checkNextPossibility(new Form(B - b, A), newFormsList.get(i), i + 1).get(1).getWidth() * checkNextPossibility(new Form(B - b, A), newFormsList.get(i), i + 1).get(1).getDepth();//
//                checkNextPossibility(new Form(B - b, A), newFormsList.get(i), i + 1).get(1).setComparator(comparator[2]);
//
//
//                comparator[3] += checkNextPossibility(new Form(A - a, b), newFormsList.get(i), i + 1).get(1).getComparator() + checkNextPossibility(new Form(A - a, b), newFormsList.get(i), i + 1).get(1).getWidth() * checkNextPossibility(new Form(A - a, b), newFormsList.get(i), i + 1).get(1).getDepth();//
//                checkNextPossibility(new Form(A - a, b), newFormsList.get(i), i + 1).get(1).setComparator(comparator[3]);
//
//
//                comparator[4] += checkNextPossibility(new Form(A - b, B), newFormsList.get(i), i + 1).get(1).getComparator() + checkNextPossibility(new Form(A - b, B), newFormsList.get(i), i + 1).get(1).getWidth() * checkNextPossibility(new Form(A - b, B), newFormsList.get(i), i + 1).get(1).getDepth();//
//                checkNextPossibility(new Form(A - b, B), newFormsList.get(i), i + 1).get(1).setComparator(comparator[4]);
//
//
//                comparator[5] += checkNextPossibility(new Form(B - a, b), newFormsList.get(i), i + 1).get(1).getComparator() + checkNextPossibility(new Form(B - a, b), newFormsList.get(i), i + 1).get(1).getWidth() * checkNextPossibility(new Form(B - a, b), newFormsList.get(i), i + 1).get(1).getDepth();//
//                checkNextPossibility(new Form(B - a, b), newFormsList.get(i), i + 1).get(1).setComparator(comparator[5]);
//
//
//                comparator[6] += checkNextPossibility(new Form(B - a, A), newFormsList.get(i), i + 1).get(1).getComparator() + checkNextPossibility(new Form(B - a, A), newFormsList.get(i), i + 1).get(1).getWidth() * checkNextPossibility(new Form(B - a, A), newFormsList.get(i), i + 1).get(1).getDepth();//
//                checkNextPossibility(new Form(B - a, A), newFormsList.get(i), i + 1).get(1).setComparator(comparator[6]);
//
//
//                comparator[7] += checkNextPossibility(new Form(A - b, a), newFormsList.get(i), i + 1).get(1).getComparator() + checkNextPossibility(new Form(A - b, a), newFormsList.get(i), i + 1).get(1).getWidth() * checkNextPossibility(new Form(A - b, a), newFormsList.get(i), i + 1).get(1).getDepth();//
//                checkNextPossibility(new Form(A - b, a), newFormsList.get(i), i + 1).get(1).setComparator(comparator[7]);
//


                for (CompareArea c : comparator) {
                    if (max < c.getSumArea())
                        max = c.getFormIndex();
                }
            }


            if (max > 0) {

                if (max == comparator[0] || max == comparator[1]) {
                    for (int c : comparator)
                        c = 0;
                    return new ArrayList<>(Arrays.asList(new Form(B, A - a), new Form(a, B - b)));
                }


                if (max == comparator[2] || max == comparator[3]) {
                    for (int c : comparator)
                        c = 0;
                    return new ArrayList<>(Arrays.asList(new Form(A - a, b), new Form(B - b, A)));
                }


                if (max == comparator[4] || max == comparator[5]) {
                    for (int c : comparator)
                        c = 0;
                    return new ArrayList<>(Arrays.asList(new Form(B - a, b), new Form(A - b, B)));
                }


                if (max == comparator[6] || max == comparator[7]) {
                    for (int c : comparator)
                        c = 0;
                    return new ArrayList<>(Arrays.asList(new Form(A - b, a), new Form(B - a, A)));
                }


            }
        }
        return fitsInForm(waste, form);
    }

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
                list.add(sort(firstWaste));
                list.add(sort(secondWaste));
                return list;
            }
            firstWaste = new Form(currWidth - dessWidth - 6, currDepth);
            secondWaste = new Form(dessWidth, currDepth - dessDepth - 6);

            list.add(sort(firstWaste));
            list.add(sort(secondWaste));
            return list;
        }

        return null;
    }

    public Form sort(Form formatka) {
        if (formatka.getWidth() > formatka.getDepth())
            return new Form(formatka.getDepth(), formatka.getWidth(), formatka.getQuantity());

        return formatka;
    }

    public List<Form> sortAreaAsc(List<Form> formList) {
        formList.sort(Comparator.comparingInt(f -> f.getWidth() * f.getDepth()));
        return formList;
    }

    public int countArea(List<Form> form) {
        int area = 0;
        for (Form f : form)
            area += f.getDepth() * f.getWidth() * f.getQuantity();
        return area;
    }

    public boolean fitsBothWays(Form f1, Form f2) {
        if (f1.getWidth() >= f2.getWidth() && f1.getDepth() >= f2.getDepth() && f1.getWidth() >= f2.getDepth() && f1.getDepth() >= f2.getWidth())
            return true;
        return false;
    }

    public Service solve() {
        String result = "Możesz wykonać ";

        //sort() --sortuj wg powierzchi(żeby sprawdzało najpierw największe detale od najmniejszych ścinek)
        //ustaw szerokości -- tak żeby zawsze krótszy bok był szerokością!!


        List<Form> resultWastesList = new ArrayList<>();


        for (int j = 0; j < newFormsList.size(); j++) {
            int i = 0;
            int quantity = 0;
            while (i < newWastesList.size() && quantity < newFormsList.get(j).getQuantity()) {


                List<Form> temp = checkNextPossibility(newWastesList.get(i), newFormsList.get(j), j);  //STRATEGIA żeby wykorzystywać jaknajwięcej długości,np z 1500x3000 zrobic 1400x1500 i zostawić 1 ścinkę

                if (!(temp.get(0).getArea() == 0 && temp.get(1).getArea() == 0)) { // TU POPRAWIAJ

                    if (!newWastesList.get(i).isCutted()) {
                        resultWastesList.add(newWastesList.get(i));
                    }

                    newWastesList.remove(i);
                    i--;
                    quantity++;

                    for (int k = 0; k < 2; k++) {
                        if (temp.get(k).getWidth() >= 6 && temp.get(k).getDepth() >= 6) {
                            temp.get(k).setCutted(true);
                            newWastesList.add(temp.get(k));
                            System.out.println(temp.get(k).toString());
                        }
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

        return new Service(resultWastesList, newFormsList);
    }


    public Service(List<Form> availableForms, List<Form> desiredForms) {
        for (int z = 0; z < availableForms.size(); z++)
            availableForms.set(z, sort(availableForms.get(z)));
        for (int z = 0; z < desiredForms.size(); z++)
            desiredForms.set(z, sort(desiredForms.get(z)));

        var n = sortAreaAsc(availableForms);
        Collections.reverse(n);
        ;
        this.newWastesList = n;
        this.newFormsList = sortAreaAsc(desiredForms);
    }


    public static List<Form> tokenizer(String text, int type) {

        Pattern pattern = Pattern.compile("-?\\d+"); // Wzorzec dla liczby całkowitej(MOżE być ujemna!!!)
        Matcher matcher = pattern.matcher(text);
        List<Form> list = new ArrayList<>();


        int width;
        int depth;
        int quantity;

        if (type == 1) {
            while (matcher.find()) {
                width = 0;
                depth = 0;
                quantity = 0;

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
                width = 0;
                depth = 0;
                quantity = 0;
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

}
