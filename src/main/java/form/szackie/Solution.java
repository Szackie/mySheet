package form.szackie;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class Solution {
    final List<Form> newWastesList;
    final List<Form> newFormsList;

    public Solution() {
        this.newWastesList=new ArrayList<>();
        this.newFormsList=new ArrayList<>();
    }

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
