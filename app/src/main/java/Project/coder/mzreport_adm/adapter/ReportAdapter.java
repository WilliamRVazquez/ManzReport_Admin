package Project.coder.mzreport_adm.adapter;


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
import Project.coder.mzreport_adm.ubicacion_reporte;

public class ReportAdapter extends FirestoreRecyclerAdapter<Report, ReportAdapter.ViewHolder> {//
    Activity activity;
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ReportAdapter(@NonNull FirestoreRecyclerOptions<Report> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int position, @NonNull Report Report) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(viewHolder.getAdapterPosition());
        final String id = documentSnapshot.getId();

        viewHolder.tiporeporte.setText(Report.getTiporeporte());//
        viewHolder.ubicacion.setText(Report.getUbicacion());//
        viewHolder.date.setText(Report.getDate().toDate().toString());

        viewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteReport(id);
            }
        });
        viewHolder.btn_detalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, Detalle_Reportes.class);
                i.putExtra("id_Reportes", id);
                activity.startActivity(i);
            }
        });
        viewHolder.btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aceptReport(id);
            }
        });

        viewHolder.btn_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, ubicacion_reporte.class);
                i.putExtra("id_Ubicacion", id);
                activity.startActivity(i);
            }
        });
    }

    private void aceptReport(String id) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this.activity);
        alert.setMessage("¿esta seguro de acpetar este reporte")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("Aceptado", "Aceptado");
                        mFirestore.collection("Reportes").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(activity, "El reporte fue aceptado", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, "Error al aceptar el reporte", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog titulo = alert.create();
        titulo.setTitle("Aceptar reporte");
        titulo.show();
    }


    private void deleteReport(String id) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this.activity);
        alert.setMessage("Seguro que quieres eliminar este reporte?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mFirestore.collection("Reportes").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {



                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(activity, "Error al eliminar", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog titulo = alert.create();
        titulo.setTitle("Elimanar Reporte");
        titulo.show();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_report_single, parent, false);

        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tiporeporte, ubicacion, date;
        ImageView btn_delete,btn_detalles,btn_ubicacion,btn_accept;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tiporeporte = itemView.findViewById(R.id.tiporeporte);
            btn_delete = itemView.findViewById(R.id.btn_eliminar);
            btn_detalles = itemView.findViewById(R.id.btn_detail);
            btn_accept = itemView.findViewById(R.id.btn_acept);
            btn_ubicacion = itemView.findViewById(R.id.btn_location);
            ubicacion = itemView.findViewById(R.id.ubicacion);
            date = itemView.findViewById(R.id.date);

        }
    }
}







