package Project.coder.mzreport_adm.adapter;


import static com.google.firebase.firestore.FieldValue.delete;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import Project.coder.mzreport_adm.Detalle_Reportes;
import Project.coder.mzreport_adm.R;
import Project.coder.mzreport_adm.model.Report;
import Project.coder.mzreport_adm.model.users;
import Project.coder.mzreport_adm.ubicacion_reporte;

public class UserAdapter extends FirestoreRecyclerAdapter<users, UserAdapter.ViewHolder> {//
    Activity activity;
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UserAdapter(@NonNull FirestoreRecyclerOptions<users> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull users users) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewHolder.getAdapterPosition());
        final String id = documentSnapshot.getId();

        viewHolder.fName.setText(users.getfName());//
        viewHolder.email.setText(users.getEmail());
        viewHolder.phone.setText(users.getPhone());
        viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteReport(id);
            }
        });
    }

    private void deleteReport(String id) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this.activity);
        alert.setMessage("Seguro que quieres eliminar a este usuario?");
        alert.setCancelable(false);
        alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Map<String, Object> map = new HashMap<>();
                map.put("borrado", true);
                mFirestore.collection("users").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(activity, "Se notifico a el usuario que su cuenta se eliminara", Toast.LENGTH_SHORT).show();
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, "Error al eliminar", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog titulo = alert.create();
        titulo.setTitle("Eliminar usuario");
        titulo.show();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ver_users, parent, false);

        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fName,email,phone;
        ImageView btn_delete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fName = itemView.findViewById(R.id.Nombre_user);
            email = itemView.findViewById(R.id.Correo_user);
            phone = itemView.findViewById(R.id.telefono_user);
            btn_delete = itemView.findViewById(R.id.btn_eliminar);


        }
    }
}