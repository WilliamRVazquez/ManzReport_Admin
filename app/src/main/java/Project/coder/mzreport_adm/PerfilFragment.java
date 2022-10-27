package Project.coder.mzreport_adm;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.concurrent.Executor;

import javax.annotation.Nullable;

public class PerfilFragment extends Fragment {
    TextView fullName, email, phone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    Button resetPassLocal, changeProfileImage, buttonsalir;
    FirebaseUser user;
    ImageView profileImage;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);

        phone = root.findViewById(R.id.profilePhone);
        fullName = root.findViewById(R.id.profileName);
        email = root.findViewById(R.id.profileEmail);
        resetPassLocal = root.findViewById(R.id.resetPasswordLocal);
        buttonsalir = root.findViewById(R.id.button);

        profileImage = root.findViewById(R.id.profileImage);
        changeProfileImage = root.findViewById(R.id.changeProfile);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();
        buttonsalir.setOnClickListener(view -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(PerfilFragment.this.getContext());
            alert.setMessage("¿Deseas cerrar sesion?")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fAuth.signOut();
                            startActivity(new Intent(PerfilFragment.this.getContext(), login.class));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog title = alert.create();
            title.setTitle("Cerrar Sesion");
            title.show();
        });
        if (fAuth.getCurrentUser() == null) {
            startActivity(new Intent(getContext(), login.class));
            onStop();
        }//sacar usuario

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this.getContext().getMainExecutor(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    phone.setText(documentSnapshot.getString("phone"));
                    fullName.setText(documentSnapshot.getString("fName"));
                    email.setText(documentSnapshot.getString("email"));

                } else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });


        resetPassLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetPassword = new EditText(v.getContext());

                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Resetiar Contraseña ?");
                passwordResetDialog.setMessage("Ingresa Nueva Contraseña > 6 Caracteres Minimo.");
                passwordResetDialog.setView(resetPassword);


                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String newPassword = resetPassword.getText().toString();
                        user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(PerfilFragment.this.getContext(), "Reseteo de Contraseña Completo.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(PerfilFragment.this.getContext(), "Error al Cambiar la Contraseña.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close
                    }
                });

                passwordResetDialog.create().show();

            }
        });

        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open gallery
                Intent i = new Intent(v.getContext(), EditProfile.class);
                i.putExtra("fullName", fullName.getText().toString());
                i.putExtra("email", email.getText().toString());
                i.putExtra("phone", phone.getText().toString());
                startActivity(i);
//

            }
        });


        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = fAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(PerfilFragment.this.getContext(), Principal.class));
        }//sacar usuario
    }
}







