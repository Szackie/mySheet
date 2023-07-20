package form.szackie;

import java.util.List;

public class Solution {
    final List<Form> newWastesList;
    final List<Form> newFormsList;

    public Solution(List<Form> usedList, List<Form> doneList) {
        this.newWastesList = usedList;
        this.newFormsList = doneList;
    }

    @SuppressWarnings("unused")
    public List<Form> getNewWastesList() {
        return newWastesList;
    }

    @SuppressWarnings("unused")
    public List<Form> getNewFormsList() {
        return newFormsList;
    }
}
