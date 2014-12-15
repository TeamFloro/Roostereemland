//package nl.floro.roostereemland.fragments;
//
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import nl.floro.roostereemland.R;
//
///**
// * Created by robert-jan on 15-12-14.
// */
//public class RoosterFragment extends Fragment {
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.rooster_fragment, container, false);
//        Bundle bundle = this.getArguments();
//        String key = bundle.getString("Klasnaam");
//        TextView textView = (TextView) view.findViewById(R.id.whoopwhoop);
//        textView.setText(key);
//
//        return view;
//    }
//
//}
