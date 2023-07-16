package form.szackie;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//todo AKTUALIZować LISTY observerem?
//todo usunąć możliwość cięcia elementów poniżej gr. 15
//todo dodać możliwość dokładnego policzenia (algorytm rekurencyjny)
//fixme uporządkować kod, wyrzucić niepotrzebne listy( naprawić dodawanie po wykonaniu), naprawić collapselist

public class Service {
    private List<Form> newWastesList;
    private List<Form> newFormsList;
    private List<Form> doneList;
    private List<Form> usedList;
    private List<Form> ZERO = new ArrayList<>();


    private List<Integer> indexes = new ArrayList<>();


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

        List<Form> resultList = new ArrayList<>();

        int i = 0;
        while (i < newWastesList.size()) {


            for (int j = 0; j < newFormsList.size(); j++) {

                resultList.clear();

                if (fits(newWastesList.get(i), newFormsList.get(j)) && !newWastesList.get(i).isUsed() && !newFormsList.get(j).isDone()) {
                    if (optimalCut(newWastesList.get(i), newFormsList.get(j)) != null)
                        resultList.addAll(optimalCut(newWastesList.get(i), newFormsList.get(j)));

                    resultList.get(0).setCutted(true);
                    resultList.get(1).setCutted(true);

                    if (!newWastesList.get(i).isCutted()) {
                        newWastesList.get(i).setParentID(newWastesList.get(i).getId());
                        resultList.get(0).setParentID(newWastesList.get(i).getId());
                        resultList.get(1).setParentID(newWastesList.get(i).getId());
                    } else {
                        resultList.get(0).setParentID(newWastesList.get(i).getParentID());
                        resultList.get(1).setParentID(newWastesList.get(i).getParentID());
                    }


                    newFormsList.get(j).setWasteID(newWastesList.get(i).getParentID());
                    newFormsList.get(j).setDone(true);
                    newWastesList.get(i).setUsed(true);

                    if (resultList.get(1).getWidth() > 0 && resultList.get(1).getDepth() > 0)
                        newWastesList.add(i + 1, setWidthDepth(resultList.get(1)));
                    if (resultList.get(0).getWidth() > 0 && resultList.get(0).getDepth() > 0)
                        newWastesList.add(i + 1, setWidthDepth(resultList.get(0)));


                }

            }
            i++;
        }


        System.out.println("Zrobione formatki: ");
        for (Form f : newFormsList) {
            if (f.isDone()) {
                setWidthDepth(f);
                doneList.add(f);
            }
        }
        collapseList(doneList);
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
        for (int q = 0; q < newWastesList.size(); q++) {
            if (newWastesList.get(q).isCutted()) {
                newWastesList.remove(q);
                q--;
            }
        }

//       collapseList(newFormsList);

        return new Service(usedList, doneList);
    }

    public void collapseList(List<Form> list) {
        int i = 0;
        while (i < list.size() - 1) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).getWidth() == list.get(j).getWidth() && list.get(i).getDepth() == list.get(j).getDepth()) {
                    list.get(i).setQuantity(list.get(i).getQuantity() + list.get(j).getQuantity());
                    list.remove(j);
                    j--;
                }
            }
            i++;
        }
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

        if (fits(existingForm, formToCreate)) {
            if ((((wWidth + 6) % (fWidth + 6)) * ((wDepth + 6) % (fDepth + 6))) >= (((wWidth + 6) % (fDepth + 6)) * ((wDepth + 6) % (fWidth + 6))))//jeśli jest opcja ułożenia inaczej, to ma ułożyć tak, żeby zostało jaknajmniej odpadu
            {
                firstWaste = new Form(wWidth - fDepth - 6, wDepth);

                secondWaste = new Form(fDepth, wDepth - fWidth - 6);
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
        for (int z = 0; z < availableForms.size(); z++)
            availableForms.set(z, setWidthDepth(availableForms.get(z)));
        for (int z = 0; z < desiredForms.size(); z++)
            desiredForms.set(z, setWidthDepth(desiredForms.get(z)));

        var n = sortAreaAsc(desiredForms);

        Collections.reverse(n);
        this.newWastesList = sortAreaAsc(availableForms);
        this.newFormsList = n;
        Form zero = new Form(0, 0);
        zero.setComparator(0);
        zero.setUsed(true);
        this.ZERO.add(zero);
        this.ZERO.add(zero);
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
