package com.example.pharma_user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class RequestActivityAdmin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private RecyclerView myRequestList;
    private DatabaseReference requestRef,userRef;
    private String currentUserId;
    private Button acceptButton,declineButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_admin);

        mAuth = FirebaseAuth.getInstance();
        currentUserId= Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("Orders");


        currentUser = mAuth.getCurrentUser();
        requestRef= FirebaseDatabase.getInstance().getReference().child("Requests");
        myRequestList = findViewById(R.id.request_admin);
        myRequestList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        InitializeFields();

    }
    private void InitializeFields() {
        declineButton = (Button) findViewById(R.id.deny_button_user_details_admin);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menus_options_admin,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.main_logout_options_admin){
            mAuth.signOut();
            SendUserToLoginAdminActivity();
        }
        return true;
    }

    private void SendUserToLoginAdminActivity() {
        Intent loginIntent = new Intent(RequestActivityAdmin.this, LoginAdminActivity.class);
        startActivity(loginIntent);
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<AccessData> options =
                new FirebaseRecyclerOptions.Builder<AccessData>()
                .setQuery(requestRef.child(currentUserId),AccessData.class)
                .build();

        FirebaseRecyclerAdapter<AccessData,RequestViewHolder> adapter =
                new FirebaseRecyclerAdapter<AccessData, RequestViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final RequestViewHolder holder, int position, @NonNull final AccessData accessData)
                    {
                        final String list_user_id = getRef(position).getKey();
                        DatabaseReference getTypeRef =getRef(position).child("request type").getRef();
                        getTypeRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot)
                            {
                                if(snapshot.exists())
                                {
                                    String type = snapshot.getValue().toString();
                                    if(type.equals("Received"))
                                    {
                                        assert list_user_id != null;
                                        userRef.child(list_user_id).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot)
                                            {
                                                if(snapshot.hasChild("Address"))
                                                {
                                                    acceptButton = (Button) findViewById(R.id.accept_button_user_details_admin);
                                                        acceptButton.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {
                                                                Intent mainIntent = new Intent(RequestActivityAdmin.this,MainActivityAdmin.class);
                                                                startActivity(mainIntent);
                                                            }
                                                        });
                                                    final String requestUserAdd = Objects.requireNonNull(snapshot.child("Address").getValue()).toString();
                                                    final String requestUserOrder = Objects.requireNonNull(snapshot.child("medicine").getValue()).toString();

                                                    holder.userAddress.setText(requestUserAdd);
                                                    holder.userOrder.setText(requestUserOrder);


                                                }
                                                else
                                                {
                                                    final String requestUserOrder = Objects.requireNonNull(snapshot.child("medicine").getValue()).toString();
                                                    holder.userOrder.setText(requestUserOrder);
                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public  RequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
                    {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_details,viewGroup,false);
                        return new RequestViewHolder(view);

                    }
                };

        myRequestList.setAdapter(adapter);
        adapter.startListening();

    }
    public static class RequestViewHolder extends RecyclerView.ViewHolder
    {
        TextView userAddress,userOrder;
        //Button acceptButton,DenyButton;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            userAddress = itemView.findViewById(R.id.user_add_admin_2);
            userOrder = itemView.findViewById(R.id.user_order_admin_2);
           // acceptButton = itemView.findViewById(R.id.accept_button_user_details_admin);
            //DenyButton = itemView.findViewById(R.id.deny_button_user_details_admin);
        }

    }

}