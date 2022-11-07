package Project.coder.mzreport_adm;

import static android.content.Context.LOCATION_SERVICE;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.maps.MapView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import Project.coder.mzreport_adm.adapter.ReportAdapter;
import Project.coder.mzreport_adm.adapter.UserAdapter;
import Project.coder.mzreport_adm.model.Report;
import Project.coder.mzreport_adm.model.users;


public class HomeFragment extends Fragment {
    public static final String TAG = "TAG";
    RecyclerView mRecycler;
    UserAdapter mAdapter;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    Query query;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mAuth = FirebaseAuth.getInstance();
        mRecycler =root.findViewById(R.id.recyclerViewSingle2);
        setUpRecyclerView();


     return root;
    }
    @SuppressLint("NotifyDataSetChanged")
    private void setUpRecyclerView() {
        mFirestore = FirebaseFirestore.getInstance();

        mRecycler.setLayoutManager(new ReportsFragment.WrapContentLinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL,false));




        query = mFirestore.collection("users");


        FirestoreRecyclerOptions<users> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<users>().setQuery(query, users.class).build();

        mAdapter = new UserAdapter(firestoreRecyclerOptions, this.getActivity());
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
    public class WrapContentLinearLayoutManager extends LinearLayoutManager {
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
