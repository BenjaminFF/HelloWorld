package com.example.benja.helloworld.SkewMesh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.benja.helloworld.RecyclerViews.MeshRecyclerAdapter;

import com.example.benja.helloworld.R;
import com.example.benja.helloworld.RecyclerViews.OnRecyclerViewItemClickListener;

import java.util.ArrayList;

public class SkewMeshWork extends AppCompatActivity {

    ArrayList<String> meshitems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skew_mesh_work);

        initmeshitems();
        initMeshRecycler();
    }

    private void initmeshitems(){
        meshitems=new ArrayList<>();
        meshitems.add("Metrix Test");
    }

    private void initMeshRecycler(){
        RecyclerView recyclerView=findViewById(R.id.mesh_recycler);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        MeshRecyclerAdapter adepter=new MeshRecyclerAdapter(meshitems);
        adepter.addRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(View itemView, String item_Title) {
                switch (item_Title){
                    case "Metrix Test":
                        Intent intent=new Intent(SkewMeshWork.this,Mesh_Metrix.class);
                        startActivity(intent);
                        break;
                }
            }
        });
        recyclerView.setAdapter(adepter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
