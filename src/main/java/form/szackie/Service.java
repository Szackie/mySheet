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

    public List<Form> checkNextPossibility(Form waste, Form form, List<Integer> checkedForms) {

        List<Form> result = new ArrayList<>();
        int indexDoneForm = -1;
        waste.setComparator(0);

        if (!fits(waste, form)) {
            Form zero=new Form(0,0);
            zero.setComparator(0);
            result.add(zero);
            result.add(zero);
            return result;
        }


        int A = waste.getWidth();
        int B = waste.getDepth();
        int a = form.getWidth();
        int b = form.getDepth();

        waste.setComparator(a * b);

        List<CompareArea> compareAreas = new ArrayList<>();
        for (int i = 0; i < 8; i++) {

            //DOBRZE, ŻE -1 NA POCZĄTKU ????????????

            CompareArea c = new CompareArea(waste.getComparator(), -1);
            compareAreas.add(c);
        }

        int max = 0;
        List<Integer> tempCheckedForms = new ArrayList<>();
        checkedForms.add(newFormsList.indexOf(form));
        tempCheckedForms.addAll(checkedForms);


        Form waste1 = new Form(B, A - a);
        for (int i = 0; i < newFormsList.size(); i++) {
            compareAreas.get(0).setSumArea(waste.getComparator());
            if (!newFormsList.get(i).isDone() && !checkedForms.contains(i) && fits(waste1, newFormsList.get(i))) {
                checkedForms.add(i);
                compareAreas.get(0).setSumArea(compareAreas.get(0).getSumArea()
                        + checkNextPossibility(waste1, newFormsList.get(i), checkedForms).get(0).getComparator()
                        + checkNextPossibility(waste1, newFormsList.get(i), checkedForms).get(1).getComparator());
            }
            waste1.setComparator(compareAreas.get(0).getSumArea());
            if (max < compareAreas.get(0).getSumArea()) {
                max = compareAreas.get(0).getSumArea();
                compareAreas.get(0).setFormIndex(i);

            }
        }
        checkedForms.clear();
        checkedForms.addAll(tempCheckedForms);


        Form waste2 = new Form(a, B - b);
        for (int i = 0; i < newFormsList.size(); i++) {
            compareAreas.get(1).setSumArea(waste.getComparator());
            if (!newFormsList.get(i).isDone() && !checkedForms.contains(i) && fits(waste2, newFormsList.get(i))) {
                checkedForms.add(i);
                compareAreas.get(1).setSumArea(compareAreas.get(1).getSumArea()
                        + checkNextPossibility(waste2, newFormsList.get(i), checkedForms).get(0).getComparator()
                        + checkNextPossibility(waste2, newFormsList.get(i), checkedForms).get(1).getComparator());
            }
            waste2.setComparator(compareAreas.get(1).getSumArea());
            if (max < compareAreas.get(1).getSumArea()) {
                max = compareAreas.get(1).getSumArea();
                compareAreas.get(1).setFormIndex(i);
            }
        }
        checkedForms.clear();
        checkedForms.addAll(tempCheckedForms);


        Form waste3 = new Form(B - b, A);
        compareAreas.get(2).setSumArea(waste.getComparator());
        for (int i = 0; i < newFormsList.size(); i++) {
            if (!newFormsList.get(i).isDone() && !checkedForms.contains(i) && fits(waste3, newFormsList.get(i))) {
                checkedForms.add(i);
                compareAreas.get(2).setSumArea(compareAreas.get(2).getSumArea()
                        + checkNextPossibility(waste3, newFormsList.get(i), checkedForms).get(0).getComparator()
                        + checkNextPossibility(waste3, newFormsList.get(i), checkedForms).get(1).getComparator());
            }
            waste3.setComparator(compareAreas.get(2).getSumArea());
            if (max < compareAreas.get(2).getSumArea()) {
                max = compareAreas.get(2).getSumArea();
                compareAreas.get(2).setFormIndex(i);
            }
        }
        checkedForms.clear();
        checkedForms.addAll(tempCheckedForms);


        Form waste4 = new Form(A - a, b);
        for (int i = 0; i < newFormsList.size(); i++) {
            compareAreas.get(3).setSumArea(waste.getComparator());
            if (!newFormsList.get(i).isDone() && !checkedForms.contains(i) && fits(waste4, newFormsList.get(i))) {
                checkedForms.add(i);
                compareAreas.get(3).setSumArea(compareAreas.get(3).getSumArea()
                        + checkNextPossibility(waste4, newFormsList.get(i), checkedForms).get(0).getComparator()
                        + checkNextPossibility(waste4, newFormsList.get(i), checkedForms).get(1).getComparator());
            }
            waste4.setComparator(compareAreas.get(3).getSumArea());
            if (max < compareAreas.get(3).getSumArea()) {
                max = compareAreas.get(3).getSumArea();
                compareAreas.get(3).setFormIndex(i);
            }
        }
        checkedForms.clear();
        checkedForms.addAll(tempCheckedForms);


        Form waste5 = new Form(A - b, B);
        for (int i = 0; i < newFormsList.size(); i++) {
            compareAreas.get(4).setSumArea(waste.getComparator());
            if (!newFormsList.get(i).isDone() && !checkedForms.contains(i) && fits(waste5, newFormsList.get(i))) {
                checkedForms.add(i);
                compareAreas.get(4).setSumArea(compareAreas.get(4).getSumArea()
                        + checkNextPossibility(waste5, newFormsList.get(i), checkedForms).get(0).getComparator()
                        + checkNextPossibility(waste5, newFormsList.get(i), checkedForms).get(1).getComparator());
            }
            waste5.setComparator(compareAreas.get(4).getSumArea());
            if (max < compareAreas.get(4).getSumArea()) {
                max = compareAreas.get(4).getSumArea();
                compareAreas.get(4).setFormIndex(i);
            }
        }
        checkedForms.clear();
        checkedForms.addAll(tempCheckedForms);


        Form waste6 = new Form(B - a, b);
        for (int i = 0; i < newFormsList.size(); i++) {
            compareAreas.get(5).setSumArea(waste.getComparator());
            if (!newFormsList.get(i).isDone() && !checkedForms.contains(i) && fits(waste6, newFormsList.get(i))) {
                checkedForms.add(i);
                compareAreas.get(5).setSumArea(compareAreas.get(5).getSumArea()
                        + checkNextPossibility(waste6, newFormsList.get(i), checkedForms).get(0).getComparator()
                        + checkNextPossibility(waste6, newFormsList.get(i), checkedForms).get(1).getComparator());
            }
            waste6.setComparator(compareAreas.get(5).getSumArea());
            if (max < compareAreas.get(5).getSumArea()) {
                max = compareAreas.get(5).getSumArea();
                compareAreas.get(5).setFormIndex(i);
            }
        }
        checkedForms.clear();
        checkedForms.addAll(tempCheckedForms);


        Form waste7 = new Form(B - a, A);
        for (int i = 0; i < newFormsList.size(); i++) {
            compareAreas.get(6).setSumArea(waste.getComparator());
            if (!newFormsList.get(i).isDone() && !checkedForms.contains(i) && fits(waste7, newFormsList.get(i))) {
                checkedForms.add(i);
                compareAreas.get(6).setSumArea(compareAreas.get(6).getSumArea()
                        + checkNextPossibility(waste7, newFormsList.get(i), checkedForms).get(0).getComparator()
                        + checkNextPossibility(waste7, newFormsList.get(i), checkedForms).get(1).getComparator());
            }
            waste7.setComparator(compareAreas.get(6).getSumArea());
            if (max < compareAreas.get(6).getSumArea()) {
                max = compareAreas.get(6).getSumArea();
                compareAreas.get(6).setFormIndex(i);
            }
        }
        checkedForms.clear();
        checkedForms.addAll(tempCheckedForms);


        Form waste8 = new Form(A - b, a);
        for (int i = 0; i < newFormsList.size(); i++) {
            compareAreas.get(7).setSumArea(waste.getComparator());
            if (!newFormsList.get(i).isDone() && !checkedForms.contains(i) && fits(waste8, newFormsList.get(i))) {
                checkedForms.add(i);
                compareAreas.get(7).setSumArea(compareAreas.get(7).getSumArea()
                        + checkNextPossibility(waste8, newFormsList.get(i), checkedForms).get(0).getComparator()
                        + checkNextPossibility(waste8, newFormsList.get(i), checkedForms).get(1).getComparator());
            }
            waste8.setComparator(compareAreas.get(7).getSumArea());
            if (max < compareAreas.get(7).getSumArea()) {
                max = compareAreas.get(7).getSumArea();
                compareAreas.get(7).setFormIndex(i);
            }
        }
        checkedForms.clear();
        checkedForms.addAll(tempCheckedForms);


        if (max == compareAreas.get(0).getSumArea()) {
            result.add(waste1);
            result.add(waste2);
            indexDoneForm = compareAreas.get(0).getFormIndex();
        }


        else if (max == compareAreas.get(1).getSumArea()) {
            result.add(waste1);
            result.add(waste2);
            indexDoneForm = compareAreas.get(1).getFormIndex();
        }


        else if (max == compareAreas.get(2).getSumArea()) {
            result.add(waste4);
            result.add(waste3);
            indexDoneForm = compareAreas.get(2).getFormIndex();
        }


        else if (max == compareAreas.get(3).getSumArea()) {
            result.add(waste4);
            result.add(waste3);
            indexDoneForm = compareAreas.get(3).getFormIndex();
        }


        else if (max == compareAreas.get(4).getSumArea()) {
            result.add(waste6);
            result.add(waste5);
            indexDoneForm = compareAreas.get(4).getFormIndex();
        }


       else if (max == compareAreas.get(5).getSumArea()) {
            result.add(waste6);
            result.add(waste5);
            indexDoneForm = compareAreas.get(5).getFormIndex();
        }


        else if (max == compareAreas.get(6).getSumArea()) {
            result.add(waste7);
            result.add(waste8);
            indexDoneForm = compareAreas.get(6).getFormIndex();
        }


        else if (max == compareAreas.get(7).getSumArea()) {
            result.add(waste7);
            result.add(waste8);
            indexDoneForm = compareAreas.get(7).getFormIndex();
        }
        System.out.println("Max: "+max);
        System.out.println(" porównywacze : "+compareAreas.get(0).getSumArea());
        System.out.println(" porównywacze : "+compareAreas.get(1).getSumArea());
        System.out.println(" porównywacze : "+compareAreas.get(2).getSumArea());
        System.out.println(" porównywacze : "+compareAreas.get(3).getSumArea());
        System.out.println(" porównywacze : "+compareAreas.get(4).getSumArea());
        System.out.println(" porównywacze : "+compareAreas.get(5).getSumArea());
        System.out.println(" porównywacze : "+compareAreas.get(6).getSumArea());
        System.out.println(" porównywacze : "+compareAreas.get(7).getSumArea());
        //  PRZEMYŚL TO JESZCZE:

//        if (compareAreas.get(0).getSumArea() == compareAreas.get(0).getSumArea() &&
//                compareAreas.get(0).getSumArea() == compareAreas.get(1).getSumArea() &&
//                compareAreas.get(0).getSumArea() == compareAreas.get(2).getSumArea() &&
//                compareAreas.get(0).getSumArea() == compareAreas.get(3).getSumArea() &&
//                compareAreas.get(0).getSumArea() == compareAreas.get(4).getSumArea() &&
//                compareAreas.get(0).getSumArea() == compareAreas.get(5).getSumArea() &&
//                compareAreas.get(0).getSumArea() == compareAreas.get(6).getSumArea() &&
//                compareAreas.get(0).getSumArea() == compareAreas.get(7).getSumArea()) {
//
//            return optimalCut(waste, form);
//        }

        if (indexDoneForm > 0) {
            newFormsList.get(indexDoneForm).setDone(true);
        }
        if(result.size()==0){
            Form zero=new Form(0,0);
            zero.setComparator(0);
            result.add(zero);
            result.add(zero);
            return result;
        }
        return result;

        //     return optimalCut(waste, form);
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

        List<Integer> integerList = new ArrayList<>();

        for (int j = 0; j < newFormsList.size(); j++) {
            int i = 0;
            while (i < newWastesList.size()) {


                if (fits(newWastesList.get(i), newFormsList.get(j)) && !newWastesList.get(i).isUsed() && !newFormsList.get(j).isDone()) {
                    integerList.add(j);
                    List<Form> checkedList = checkNextPossibility(newWastesList.get(i), newFormsList.get(j), integerList);

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
            }
        }
        for (Form f : doneList) {
            System.out.println(f);
        }
        System.out.println("Użyte ścinki: ");
        for (Form w : newWastesList) {
            if (w.isUsed() && !w.isCutted()) {
                usedList.add(w);
            }
        }
        for (Form w : usedList) {
            System.out.println(w);
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

    public void reset() {

        this.newWastesList.clear();
        this.newFormsList.clear();
        this.doneList.clear();
        this.usedList.clear();

    }
}
