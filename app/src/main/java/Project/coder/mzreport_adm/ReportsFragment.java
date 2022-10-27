package Project.coder.mzreport_adm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ReportsFragment extends Fragment {

    ListView l;
    String tutorials[]
            = { "Algorithms", "Data Structures",
            "Languages", "Interview Corner",
            "GATE", "ISRO CS",
            "UGC NET CS", "CS Subjects",
            "Web Technologies","Algorithms", "Data Structures",
            "Languages", "Interview Corner",
            "GATE", "ISRO CS",
            "UGC NET CS", "CS Subjects",
            "Web Technologies" };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_reports, container, false);
        //recordar cambiar el return por otra variable y crear el return abajo
        l =root.findViewById(R.id.list);
        ArrayAdapter<String> arr;
        //arr = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,tutorials);
        arr = new ArrayAdapter<String>(getContext(),R.layout.listviewresource,tutorials);
        l.setAdapter(arr);

        return root;
    }
}