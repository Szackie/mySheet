package form.szackie;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Service {// OBSERVERA DODAJ!!! AKTUALIZUJ LISTY (?)
    private List<Form> newWastesList;
    private List<Form> newFormsList;
    private List<Form> doneList;
    private List<Form> usedList;

    private List<Integer> indexes=new ArrayList<>();


    public List<Form> expandList() {

        for (int i = 0; i < newFormsList.size(); i++) {

            int quantity = newFormsList.get(i).getQuantity();
            if (quantity > 1) {
                newFormsList.get(i).setQuantity(1);
                for (int j = 1; j < quantity; j++) {
                    newFormsList.add(i + j, new Form(newFormsList.get(i).getWidth(), newFormsList.get(i).getDepth()));
                }
            }
        }
        return newFormsList;
    }

    public Service solve() {

        this.newFormsList = expandList();

        for (Form el : this.newFormsList)
            System.out.println("expanded list: " + el.toString());


        List<Integer> integerList = new ArrayList<>();
        List<Form> resultList=new ArrayList<>();

        int i = 0;
        while (i < newWastesList.size()) {

//            for(int j=0;j< newFormsList.size();j++) {

                resultList.clear();
                resultList=checkNextPossibility(newWastesList.get(i), newFormsList.get(0), integerList);
                
//                if (fits(newWastesList.get(i), newFormsList.get(j)) && !newWastesList.get(i).isUsed() && !newFormsList.get(j).isDone() && j==resultList.get(0).getWasteID()) {
//
//                    resultList.get(0).setCutted(true);
//                    resultList.get(1).setCutted(true);
//
//                    if (!newWastesList.get(i).isCutted()) {
//                        newWastesList.get(i).setParentID(newWastesList.get(i).getId());
//                        resultList.get(0).setParentID(newWastesList.get(i).getId());
//                        resultList.get(1).setParentID(newWastesList.get(i).getId());
//                    } else {
//                        resultList.get(0).setParentID(newWastesList.get(i).getParentID());
//                        resultList.get(1).setParentID(newWastesList.get(i).getParentID());
//                    }
//
//
//                    newFormsList.get(j).setWasteID(newWastesList.get(i).getParentID());
//                    newFormsList.get(j).setDone(true);
//                    newWastesList.get(i).setUsed(true);
//
//                    newWastesList.add(i+1,setWidthDepth(resultList.get(1)));
//                    newWastesList.add(i+1,setWidthDepth(resultList.get(0)));
//

//                }

          //  }
            i++;
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
        System.out.println("nWL: ");
        for (Form w : newWastesList) {

            System.out.println(w.toString());

        }
        System.out.println(" INDEXES : ");
        for (Integer o:indexes){
            if(o>=0)
            System.out.println(newFormsList.get(o));
        }
        return new Service(usedList, doneList);
    }

    public List<Form> checkNextPossibility(Form waste, Form form, List<Integer> checkedForms) {

        List<Form> result = new ArrayList<>();
        int indexDoneForm = -1;


        if (!fits(waste, form) || form.isDone() || waste.isUsed()) {
            waste.setComparator(0);
            Form zero0 = new Form(0, 0);
            Form zero1 = new Form(0, 0);
            zero1.setComparator(0);
            zero0.setComparator(0);
            result.add(zero1);
            result.add(zero0);
        } else {


            int A = waste.getWidth();
            int B = waste.getDepth();
            int a = form.getWidth();
            int b = form.getDepth();

            waste.setComparator(a * b);

            int[] maxArr = new int[8];

            for (int i = 0; i < 8; i++) {
                maxArr[i] = 0;
            }
            List<CompareArea> compareAreas = new ArrayList<>();
            for (int i = 0; i < 8; i++) {


                CompareArea c = new CompareArea(waste.getComparator(), -1);
                compareAreas.add(c);
            }

            List<Integer> tempCheckedForms = new ArrayList<>(checkedForms);


            Form waste1 = new Form(B, A - a);
            Form waste2 = new Form(a, B - b);
            for (int i = 0; i < newFormsList.size(); i++) {
                System.out.println(":::::::::: formatka:" + newFormsList.get(i).toString() + ";;;;;;;;;ścina: " + waste.toString() + ";;;;;;;;;ścinka ze ścinki: " + waste1.toString());
                compareAreas.get(0).setSumArea(waste.getComparator());

                System.out.println("WASTE!" + waste1);
                System.out.println("FORM!" + newFormsList.get(i));
                System.out.println("FITS?" + fits(waste1, newFormsList.get(i)));

                if (!newFormsList.get(i).isDone() && !checkedForms.contains(i) && (fits(waste1, newFormsList.get(i)) || fits(waste2, newFormsList.get(i)))) {
                    checkedForms.add(i);
                    System.out.println();
                    System.out.println(" COMP before: " + compareAreas.get(0).getSumArea());
                    compareAreas.get(0).setSumArea(compareAreas.get(0).getSumArea()
                            + checkNextPossibility(waste1, newFormsList.get(i), checkedForms).get(0).getComparator()
                            + checkNextPossibility(waste1, newFormsList.get(i), checkedForms).get(1).getComparator()
                            + checkNextPossibility(waste2, newFormsList.get(i), checkedForms).get(0).getComparator()
                            + checkNextPossibility(waste2, newFormsList.get(i), checkedForms).get(1).getComparator());
                    System.out.println(" COMP after: " + compareAreas.get(0).getSumArea());
                }
//            waste1.setComparator(compareAreas.get(0).getSumArea());
                checkedForms.clear();
                checkedForms.addAll(tempCheckedForms);
                if (maxArr[0] < compareAreas.get(0).getSumArea()) {
                    maxArr[0] = compareAreas.get(0).getSumArea();
                    compareAreas.get(0).setFormIndex(i);

                }
            }


            Form waste3 = new Form(B - b, A);
            Form waste4 = new Form(A - a, b);
            compareAreas.get(1).setSumArea(waste.getComparator());
            for (int i = 0; i < newFormsList.size(); i++) {
                if (!newFormsList.get(i).isDone() && !checkedForms.contains(i) && (fits(waste3, newFormsList.get(i)) || fits(waste4, newFormsList.get(i)))) {
                    checkedForms.add(i);
                    compareAreas.get(1).setSumArea(compareAreas.get(1).getSumArea()
                            + checkNextPossibility(waste3, newFormsList.get(i), checkedForms).get(0).getComparator()
                            + checkNextPossibility(waste3, newFormsList.get(i), checkedForms).get(1).getComparator()
                            + checkNextPossibility(waste4, newFormsList.get(i), checkedForms).get(0).getComparator()
                            + checkNextPossibility(waste4, newFormsList.get(i), checkedForms).get(1).getComparator());
                }
//            waste3.setComparator(compareAreas.get(2).getSumArea());
                checkedForms.clear();
                checkedForms.addAll(tempCheckedForms);
                if (maxArr[1] < compareAreas.get(1).getSumArea()) {
                    maxArr[1] = compareAreas.get(1).getSumArea();
                    compareAreas.get(1).setFormIndex(i);
                }
            }


            Form waste5 = new Form(A - b, B);
            Form waste6 = new Form(B - a, b);
            for (int i = 0; i < newFormsList.size(); i++) {
                compareAreas.get(2).setSumArea(waste.getComparator());
                if (!newFormsList.get(i).isDone() && !checkedForms.contains(i) && (fits(waste5, newFormsList.get(i)) || fits(waste6, newFormsList.get(i)))) {
                    checkedForms.add(i);
                    compareAreas.get(2).setSumArea(compareAreas.get(2).getSumArea()
                            + checkNextPossibility(waste5, newFormsList.get(i), checkedForms).get(0).getComparator()
                            + checkNextPossibility(waste5, newFormsList.get(i), checkedForms).get(1).getComparator()
                            + checkNextPossibility(waste6, newFormsList.get(i), checkedForms).get(0).getComparator()
                            + checkNextPossibility(waste6, newFormsList.get(i), checkedForms).get(1).getComparator());
                }
                checkedForms.clear();
                checkedForms.addAll(tempCheckedForms);
//            waste5.setComparator(compareAreas.get(4).getSumArea());
                if (maxArr[2] < compareAreas.get(2).getSumArea()) {
                    maxArr[2] = compareAreas.get(2).getSumArea();
                    compareAreas.get(2).setFormIndex(i);
                }
            }


            Form waste7 = new Form(B - a, A);
            Form waste8 = new Form(A - b, a);
            for (int i = 0; i < newFormsList.size(); i++) {
                compareAreas.get(3).setSumArea(waste.getComparator());
                if (!newFormsList.get(i).isDone() && !checkedForms.contains(i) && (fits(waste7, newFormsList.get(i)) || fits(waste8, newFormsList.get(i)))) {
                    checkedForms.add(i);
                    compareAreas.get(3).setSumArea(compareAreas.get(3).getSumArea()
                            + checkNextPossibility(waste7, newFormsList.get(i), checkedForms).get(0).getComparator()
                            + checkNextPossibility(waste7, newFormsList.get(i), checkedForms).get(1).getComparator()
                            + checkNextPossibility(waste8, newFormsList.get(i), checkedForms).get(0).getComparator()
                            + checkNextPossibility(waste8, newFormsList.get(i), checkedForms).get(1).getComparator());
                }
                checkedForms.clear();
                checkedForms.addAll(tempCheckedForms);
//            waste7.setComparator(compareAreas.get(6).getSumArea());
                if (maxArr[3] < compareAreas.get(3).getSumArea()) {
                    maxArr[3] = compareAreas.get(3).getSumArea();
                    compareAreas.get(3).setFormIndex(i);
                }
            }


            int max = Arrays.stream(maxArr).max().orElse(Integer.MIN_VALUE);

            if (max == maxArr[0]) {
                result.add(waste1);
                result.add(waste2);
                indexDoneForm = compareAreas.get(0).getFormIndex();

            } else if (max == maxArr[1]) {
                result.add(waste3);
                result.add(waste4);
                indexDoneForm = compareAreas.get(1).getFormIndex();

            } else if (max == maxArr[2]) {
                result.add(waste5);
                result.add(waste6);
                indexDoneForm = compareAreas.get(2).getFormIndex();

            } else if (max == maxArr[3]) {
                result.add(waste7);
                result.add(waste8);
                indexDoneForm = compareAreas.get(3).getFormIndex();

            }
            System.out.println("Max: " + max);
            System.out.println(" porównywacze : " + maxArr[0]);
            System.out.println(" porównywacze : " + maxArr[1]);
            System.out.println(" porównywacze : " + maxArr[2]);
            System.out.println(" porównywacze : " + maxArr[3]);

            //  PRZEMYŚL TO JESZCZE2

            result.get(1).setComparator(max);
            result.get(0).setComparator(max);
            if (compareAreas.get(0).getSumArea() == compareAreas.get(0).getSumArea() &&
                    compareAreas.get(0).getSumArea() == compareAreas.get(1).getSumArea() &&
                    compareAreas.get(0).getSumArea() == compareAreas.get(2).getSumArea() &&
                    compareAreas.get(0).getSumArea() == compareAreas.get(3).getSumArea() ) {
                result.clear();
                result = optimalCut(waste, form);
                result.get(1).setComparator(max);
                result.get(0).setComparator(max);

            }
        } //else end


//        if (indexDoneForm > 0) {
//            newFormsList.get(indexDoneForm).setDone(true);
//        }


        //     return optimalCut(waste, form);

        indexes.add(indexDoneForm);

        if(newFormsList.indexOf(form)==indexDoneForm)
            //fixme roboczo na szybko przekazuję index formatki dalej:
            result.get(0).setWasteID(indexDoneForm);
        else
            result.get(0).setWasteID(-1);

        return result;
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
