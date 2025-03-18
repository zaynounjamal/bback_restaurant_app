package zaynoun.ul.bbackapplication.adapters;

import java.util.ArrayList;

import zaynoun.ul.bbackapplication.models.HomeVerModel;

public interface UpdateVerticalRec {
    void callBack(int position, String category);

    public void callBack(int position, ArrayList<HomeVerModel> list);
}
