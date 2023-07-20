package form.szackie;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//todo AKTUALIZować LISTY observerem?
//todo usunąć możliwość cięcia elementów poniżej gr. 15
//todo dodać możliwość dokładnego policzenia (algorytm rekurencyjny)
//fixme uporządkować kod, wyrzucić niepotrzebne listy( naprawić dodawanie po wykonaniu), naprawić collapselist

public class Service {
    private final List<Form> newWastesList = new ArrayList<>();
    private List<Form> newFormsList = new ArrayList<>();
    private final List<Form> doneList = new ArrayList<>();
    private final List<Form> usedList = new ArrayList<>();

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

    public Solution solve() {

        newFormsList = expandList();

        List<Form> resultList = new ArrayList<>();

        int i = 0;
        while (i < newWastesList.size()) {

            for (Form form : newFormsList) {

                resultList.clear();

                if (fits(newWastesList.get(i), form) && !newWastesList.get(i).isUsed() && !form.isDone()) {
                    if (optimalCut(newWastesList.get(i), form) != null)
                        resultList.addAll(optimalCut(newWastesList.get(i), form));

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

                    form.setWasteID(newWastesList.get(i).getParentID());
                    form.setDone(true);
                    newWastesList.get(i).setUsed(true);

                    if (resultList.get(1).getWidth() > 0 && resultList.get(1).getDepth() > 0)
                        newWastesList.add(i + 1, resultList.get(1));
                    if (resultList.get(0).getWidth() > 0 && resultList.get(0).getDepth() > 0)
                        newWastesList.add(i + 1, resultList.get(0));
                }

            }
            i++;
        }

        for (Form f : newFormsList) {
            if (f.isDone()) {

                doneList.add(f);

            }
        }

        collapseList(doneList);

        for (Form w : newWastesList) {
            if (w.isUsed() && !w.isCutted()) {
                usedList.add(w);
            }
        }

        for (int q = 0; q < newWastesList.size(); q++) {
            if (newWastesList.get(q).isCutted()) {
                newWastesList.remove(q);
                q--;
            }
        }
        return new Solution(usedList, doneList);
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

        return biggerForm.getDepth() >= smallerForm.getDepth() && biggerForm.getWidth() >= smallerForm.getWidth() && biggerForm.getWidth() > 0 && biggerForm.getDepth() > 0 && smallerForm.getWidth() > 0 && smallerForm.getDepth() > 0;
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
                list.add((firstWaste));
                list.add((secondWaste));
                return list;
            }
            firstWaste = new Form(wWidth - fWidth - 6, wDepth);
            secondWaste = new Form(fWidth, wDepth - fDepth - 6);

            list.add((firstWaste));
            list.add((secondWaste));
            return list;
        }

        return null;
    }

    public void sortAreaAsc(List<Form> formList) {
        formList.sort(Comparator.comparingInt(f -> f.getWidth() * f.getDepth()));

    }

    public Service(List<Form> availableForms, List<Form> desiredForms) {

        sortAreaAsc(desiredForms);
        Collections.reverse(desiredForms);
        sortAreaAsc(availableForms);

        for (int z = 0; z < availableForms.size(); z++)
            this.newWastesList.add(z, makeCopy(availableForms.get(z)));
        for (int z = 0; z < desiredForms.size(); z++)
            this.newFormsList.add(z, makeCopy(desiredForms.get(z)));

    }

    public Form makeCopy(Form form) {
        Form newForm = new Form(form.getWidth(), form.getDepth(), form.getQuantity());
        newForm.setCutted(form.isCutted());
        newForm.setUsed(form.isUsed());
        newForm.setParentID(form.getParentID());
        newForm.setDone(form.isDone());
        newForm.setWasteID(form.getWasteID());
        return newForm;
    }

    public static List<Form> tokenizer(String text, int type) {

        Pattern pattern = Pattern.compile("-?\\d+"); //fixme Wzorzec dla liczby całkowitej(MOżE być ujemna!!!)
        Matcher matcher = pattern.matcher(text);
        List<Form> list = new ArrayList<>();

        int width;
        int depth;
        int quantity;

        if (type == 1) {
            while (matcher.find()) {
                depth = 0;
                quantity = 0;

                width = Integer.parseInt(matcher.group());

                if (matcher.find()) {
                    depth = Integer.parseInt(matcher.group());
                }
                if (matcher.find()) {
                    quantity = Integer.parseInt(matcher.group());
                }
                if (width > 0 && depth > 0 && quantity > 0)
                    list.add(new Form(width, depth, quantity));
            }
        } else {
            while (matcher.find()) {
                depth = 0;
                quantity = 0;
                width = Integer.parseInt(matcher.group());

                if (matcher.find()) {
                    depth = Integer.parseInt(matcher.group());
                    quantity = 1;
                }
                if (width > 0 && depth > 0)
                    list.add(new Form(width, depth, quantity));
            }

        }
        return list;
    }


    public void reset() {

        this.newWastesList.clear();
        this.newFormsList.clear();
        this.doneList.clear();
        this.usedList.clear();

    }
}
