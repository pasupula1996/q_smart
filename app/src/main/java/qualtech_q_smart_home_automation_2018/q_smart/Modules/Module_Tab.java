package qualtech_q_smart_home_automation_2018.q_smart.Modules;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import qualtech_q_smart_home_automation_2018.q_smart.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Module_Tab extends Fragment {


    public Module_Tab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.module__tab, container, false);
    }

}
