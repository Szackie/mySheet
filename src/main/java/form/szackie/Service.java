package form.szackie;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {// OBSERVERA DODAJ!!! AKTUALIZUJ LISTY (?)
    private List<Form> newWastesList;
    private List<Form> newFormsList;
    private List<Form> doneList;
    private List<Form> usedList;

    public List<Form> expandList() {

        for (int i = 0; i < newFormsList.size(); i++) {

            int quantity = newFormsList.get(i).getQuantity();
            if (quantity > 1)
                newFormsList.get(i).setQuantity(1);
            for (int j = 1; j < quantity; j++) {
                newFormsList.add(i + j, newFormsList.get(i));
            }
        }
        return newFormsList;
    }

    public List<Form> checkNextPossibility(Form waste, Form form, int startIndex) {
        if (optimalCut(waste, form) == null)
            return new ArrayList<>(Arrays.asList(new Form(0, 0), new Form(0, 0)));


        int A = waste.getWidth();
        int B = waste.getDepth();
        int a = form.getWidth();
        int b = form.getDepth();

        List<CompareArea> compareAreas = new ArrayList<>(8);

        for (int i = 0; i < 8; i++) {
            CompareArea c = new CompareArea(form.getArea(), startIndex);
            compareAreas.add(c);
        }

        int maxComparator = 0;

        if ( fitsBothWays(waste, form)) {
            for (int i = startIndex; i < newFormsList.size(); i++) {

                compareAreas.get(0).setSumArea(compareAreas.get(0).getSumArea() + checkNextPossibility(new Form(B, A - a), newFormsList.get(i), i + 1).get(1).getComparator());
                checkNextPossibility(new Form(B, A - a), newFormsList.get(i), i + 1).get(1).setComparator(compareAreas.get(0).getSumArea());


                compareAreas.get(1).setSumArea(compareAreas.get(1).getSumArea() + checkNextPossibility(new Form(a, B - b), newFormsList.get(i), i + 1).get(1).getComparator());
                checkNextPossibility(new Form(a, B - b), newFormsList.get(i), i + 1).get(1).setComparator(compareAreas.get(1).getSumArea());


                compareAreas.get(2).setSumArea(compareAreas.get(2).getSumArea() + checkNextPossibility(new Form(B - b, A), newFormsList.get(i), i + 1).get(1).getComparator());
                checkNextPossibility(new Form(B - b, A), newFormsList.get(i), i + 1).get(1).setComparator(compareAreas.get(2).getSumArea());


                compareAreas.get(3).setSumArea(compareAreas.get(3).getSumArea() + checkNextPossibility(new Form(A - a, b), newFormsList.get(i), i + 1).get(1).getComparator());
                checkNextPossibility(new Form(A - a, b), newFormsList.get(i), i + 1).get(1).setComparator(compareAreas.get(3).getSumArea());


                compareAreas.get(4).setSumArea(compareAreas.get(4).getSumArea() + checkNextPossibility(new Form(A - b, B), newFormsList.get(i), i + 1).get(1).getComparator());
                checkNextPossibility(new Form(A - b, B), newFormsList.get(i), i + 1).get(1).setComparator(compareAreas.get(4).getSumArea());


                compareAreas.get(5).setSumArea(compareAreas.get(5).getSumArea() + checkNextPossibility(new Form(B - a, b), newFormsList.get(i), i + 1).get(1).getComparator());
                checkNextPossibility(new Form(B - a, b), newFormsList.get(i), i + 1).get(1).setComparator(compareAreas.get(5).getSumArea());


                compareAreas.get(6).setSumArea(compareAreas.get(6).getSumArea() + checkNextPossibility(new Form(B - a, A), newFormsList.get(i), i + 1).get(1).getComparator());
                checkNextPossibility(new Form(B - a, A), newFormsList.get(i), i + 1).get(1).setComparator(compareAreas.get(6).getSumArea());


                compareAreas.get(7).setSumArea(compareAreas.get(7).getSumArea() + checkNextPossibility(new Form(A - b, a), newFormsList.get(i), i + 1).get(1).getComparator());
                checkNextPossibility(new Form(A - b, a), newFormsList.get(i), i + 1).get(1).setComparator(compareAreas.get(7).getSumArea());


            }
            for (CompareArea c : compareAreas) {
                if (maxComparator < c.getSumArea()) {
                    maxComparator = c.getSumArea();
                }
            }

            if (maxComparator > 0) {



                System.out.println(B+" "+(A - a)+" "+a+" " +(B - b));

                System.out.println((A - a)+" "+ b+" "+(B - b)+" "+ A);

                System.out.println((B - a)+" "+ b+" "+(A - b) +" "+B);

                System.out.println((A - b)+" "+ a+" "+(B - a)+" "+ A);

                if (maxComparator == compareAreas.get(0).getSumArea() || maxComparator == compareAreas.get(1).getSumArea()) {
                    System.out.println("A");
                    return new ArrayList<>(Arrays.asList(new Form(B, A - a), new Form(a, B - b)));
                }


                if (maxComparator == compareAreas.get(2).getSumArea() || maxComparator == compareAreas.get(3).getSumArea()) {

                    System.out.println("B");
                    return new ArrayList<>(Arrays.asList(new Form(A - a, b), new Form(B - b, A)));
                }


                if (maxComparator == compareAreas.get(4).getSumArea() || maxComparator == compareAreas.get(5).getSumArea()) {

                    System.out.println("C");
                    return new ArrayList<>(Arrays.asList(new Form(B - a, b), new Form(A - b, B)));
                }


                if (maxComparator == compareAreas.get(6).getSumArea() || maxComparator == compareAreas.get(7).getSumArea()) {

                    System.out.println("D");
                    return new ArrayList<>(Arrays.asList(new Form(A - b, a), new Form(B - a, A)));
                }

            }
        }
        return optimalCut(waste, form);
    }

    public boolean fits(Form biggerForm, Form smallerForm) {
        Form w = setWidthDepth(biggerForm);
        Form f = setWidthDepth(smallerForm);

        if (w.getDepth() >= f.getDepth() && w.getWidth() >= f.getWidth() && w.getWidth() > 0 && w.getDepth() > 0 && f.getWidth() > 0 && f.getDepth() > 0)
            return true;

        return false;
    }

    public List<Form> optimalCut(Form existingForm, Form formToCreate) {
        Form firstWaste;
        Form secondWaste;
        List<Form> list = new ArrayList<>();

        int wDepth = existingForm.getDepth();
        int fDepth = formToCreate.getDepth();
        int wWidth = existingForm.getWidth();
        int fWidth = formToCreate.getWidth();

        if (fits(existingForm, formToCreate)) { //sprawdzam, czy się zmieści
            if ((((wWidth + 6) % (fWidth + 6)) * ((wDepth + 6) % (fDepth + 6))) >= (((wWidth + 6) % (fDepth + 6)) * ((wDepth + 6) % (fWidth + 6))))//jeśli jest opcja ułożenia inaczej, to ma ułożyć tak, żeby zostało jaknajmniej odpadu
            {
                firstWaste = new Form(wWidth - fDepth - 6, wDepth);  //MOGĄ WYCHODZIĆ TU UJEMNE(OGARNIJ)

                secondWaste = new Form(fDepth, wDepth - fWidth - 6); //MOGĄ WYCHODZIĆ TU UJEMNE(OGARNIJ)
                list.add(setWidthDepth(firstWaste));
                list.add(setWidthDepth(secondWaste));
                return list;
            }
            firstWaste = new Form(wWidth - fWidth - 6, wDepth);
            secondWaste = new Form(fWidth, wDepth - fDepth - 6);

            list.add(setWidthDepth(firstWaste));
            list.add(setWidthDepth(secondWaste));
            return list;
        }

        return null;
    }

    public Form setWidthDepth(Form formatka) {
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


        this.newFormsList = expandList();

        for (int j = 0; j < newFormsList.size(); j++) {
            int i = 0;
            while (i < newWastesList.size()) {


                if (fits(newWastesList.get(i), newFormsList.get(j)) && !newWastesList.get(i).isUsed() && !newFormsList.get(j).isDone()) {
                    List<Form> checkedList = checkNextPossibility(newWastesList.get(i), newFormsList.get(j), j);
                    checkedList.get(0).setCutted(true);
                    checkedList.get(1).setCutted(true);

                    if (!newWastesList.get(i).isCutted()) {
                        newWastesList.get(i).setParentID(newWastesList.get(i).getId());
                        checkedList.get(0).setParentID(newWastesList.get(i).getId());
                        checkedList.get(1).setParentID(newWastesList.get(i).getId());
                    } else {
                        checkedList.get(0).setParentID(newWastesList.get(i).getParentID());
                        checkedList.get(1).setParentID(newWastesList.get(i).getParentID());
                    }
                    newFormsList.get(j).setWasteID(newWastesList.get(i).getParentID());
                    newFormsList.get(j).setDone(true);
                    newWastesList.get(j).setUsed(true);

                    newWastesList.add(i + 1, setWidthDepth(checkedList.get(1)));
                    newWastesList.add(i + 1, setWidthDepth(checkedList.get(0)));

                }
                i++;
            }

        }
        System.out.println("Zrobione formatki: ");
        for (Form f : newFormsList) {
            if (f.isDone()) {
                doneList.add(f);
                System.out.println(f);
            }
        }
        System.out.println("Użyte ścinki: ");
        for (Form w : newWastesList) {
            if (w.isUsed() && !w.isCutted()) {
                usedList.add(w);
                System.out.println(w);
            }
        }

        return new Service(usedList, doneList);
    }


    public Service(List<Form> availableForms, List<Form> desiredForms) {
        this.doneList = new ArrayList<>();
        this.usedList = new ArrayList<>();
//        for (int z = 0; z < availableForms.size(); z++)
//            availableForms.set(z, setWidthDepth(availableForms.get(z)));
//        for (int z = 0; z < desiredForms.size(); z++)
//            desiredForms.set(z, setWidthDepth(desiredForms.get(z)));
//
//        var n = sortAreaAsc(availableForms);
//        Collections.reverse(n);
        this.newWastesList = availableForms;
        this.newFormsList = desiredForms;
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

    public List<Form> getDoneList() {
        return doneList;
    }

    public void setDoneList(List<Form> doneList) {
        this.doneList = doneList;
    }

    public List<Form> getUsedList() {
        return usedList;
    }

    public void setUsedList(List<Form> usedList) {
        this.usedList = usedList;
    }
}
