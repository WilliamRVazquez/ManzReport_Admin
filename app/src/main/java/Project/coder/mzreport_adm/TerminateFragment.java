package Project.coder.mzreport_adm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import Project.coder.mzreport_adm.adapter.TerminadoAdapter;
import Project.coder.mzreport_adm.model.terminado;

public class TerminateFragment extends Fragment {

    public static final String TAG = "TAG";
    RecyclerView mRecycler;
    TerminadoAdapter mAdapter;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    Query query;
    String correo_e;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFirestore = FirebaseFirestore.getInstance();
        View root = inflater.inflate(R.layout.fragment_terminate, container, false);
        mAuth = FirebaseAuth.getInstance();

        SharedPreferences prefs = getActivity().getSharedPreferences("Preferences", 0);
        correo_e = prefs.getString("users", "");
        mRecycler =root.findViewById(R.id.recyclerViewSingle);

        query = mFirestore.collection("Reportes").whereEqualTo("Aceptado", "Terminado");
        setUpRecyclerView();

        // Inflate the layout for this fragment
        return root;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setUpRecyclerView() {

        mRecycler.setLayoutManager(new WrapContentLinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL,false));

        //query = mFirestore.collection("Reportes").whereEqualTo("Aceptado", "Terminado");

        FirestoreRecyclerOptions<terminado> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<terminado>().setQuery(query, terminado.class).build();

        mAdapter = new TerminadoAdapter(firestoreRecyclerOptions, this.getActivity());
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
    public static class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context) {
            super(context);
        }

        public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                Log.e("TAG", "meet a IOOBE in RecyclerView");
            }
        }
    }
}