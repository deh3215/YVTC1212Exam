package com.example.a32150.yvtc1212exam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    final static String TAG="DATA";
    EditText et;
    ListView lv;
    ArrayAdapter<String> adapter;
    DatabaseReference myRef;
    String str;
    ArrayList arrayList,arrayList2;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        et = (EditText)findViewById(R.id.editText);
        lv = (ListView)findViewById(R.id.listView);

        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onResume() {
        super.onResume();
        arrayList = new ArrayList();
        arrayList2 = new ArrayList();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                arrayList2.clear();
                for(DataSnapshot ds : dataSnapshot.child("Chat").getChildren()){
                    arrayList.add(ds.getValue());
                }
                if(arrayList.size()!=0) {
                    for (int i = arrayList.size()-1; i > 0; i--) {
                        arrayList2.add(arrayList.get(i));
                    }
                }
                adapter = new ArrayAdapter(ChatActivity.this,android.R.layout.simple_list_item_1, arrayList2);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onClick(View v)   {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count=0;
                for(DataSnapshot ds:dataSnapshot.child("Chat").getChildren()){
                    count++;
                }
                str = et.getText().toString();
                if(!str.equals("")){
                    myRef.child("Chat").child(count+"").setValue(str);
                    et.setText("");
                    count=0;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
