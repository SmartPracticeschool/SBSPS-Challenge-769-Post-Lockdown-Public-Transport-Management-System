package com.example.e_transpo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class feedback_Healthplus extends AppCompatActivity {
    private RecyclerView mhealthplusview;
    FirebaseFirestore fbase;
    private  FirestoreRecyclerAdapter adapter;
    FirebaseAuth fauth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback__healthplus);
        mhealthplusview = findViewById(R.id.recyclerview_healthplus);
        fbase = FirebaseFirestore.getInstance();
        fauth = FirebaseAuth.getInstance();

        Query query = fbase.collection("Health Plus Messages");

        FirestoreRecyclerOptions<modalclass> options = new FirestoreRecyclerOptions.Builder<modalclass>().setQuery(query,modalclass.class).build();


         adapter = new FirestoreRecyclerAdapter<modalclass, myviewholder>(options) {
             @NonNull
             @Override
             public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                 View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.healthplus_list_itmes,parent,false);
                 return new myviewholder(view);
             }

             @Override
             protected void onBindViewHolder(@NonNull myviewholder myviewholder, int i, @NonNull modalclass modalclass) {
                 if(modalclass.getName_of_Patient().isEmpty()){
                     myviewholder.list_patientname.setText("No Patients Sumbitted !!");
                 }
                 else{
                     myviewholder.list_patientname.setText("Name Of Patient: "+modalclass.getName_of_Patient());
                     myviewholder.list_healthissue.setText("Health Issue: "+modalclass.getHeath_Issue());
                     myviewholder.list_patientid.setText("Patient-Id(app user id)z; "+modalclass.getUser_ID_of_Patient());
                     myviewholder.list_userid.setText("User-Id: "+modalclass.getUser_ID_of_user());
                 }


             }
         };
            mhealthplusview.setLayoutManager(new LinearLayoutManager(this));
            mhealthplusview.setAdapter(adapter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }


    private class myviewholder extends RecyclerView.ViewHolder{
        private TextView list_patientname,list_userid,list_patientid,list_healthissue;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            list_patientname = itemView.findViewById(R.id.paitentname);
            list_userid = itemView.findViewById(R.id.patientid);
            list_patientid = itemView.findViewById(R.id.userid_healthissue);
            list_healthissue = itemView.findViewById(R.id.healthissue);
        }
    }
}