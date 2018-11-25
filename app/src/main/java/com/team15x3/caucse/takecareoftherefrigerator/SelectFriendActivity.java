package com.team15x3.caucse.takecareoftherefrigerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SelectFriendActivity extends AppCompatActivity {
    ChatModel chatModel = new ChatModel();
    private String[] aa;
    private String refiename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friend);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.selectFriendActivity_recyclerview);
        recyclerView.setAdapter(new SelectFriendRecyclerViewAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button button = (Button) findViewById(R.id.selectFriendActivity_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                chatModel.users.put(myUid,true);

                FirebaseDatabase.getInstance().getReference().child("chatrooms").push().setValue(chatModel);

               // chatModel.users.clear();
                FirebaseDatabase.getInstance().getReference().child("users").child(myUid).child("grouprefriList").child(User.INSTANCE.getRefrigeratorList().
                        get(User.INSTANCE.getCurrentRefrigerator()).
                        getName()).setValue(chatModel);


                 refiename = User.INSTANCE.getRefrigeratorList().
                         get(User.INSTANCE.getCurrentRefrigerator()).
                         getName().toString();

                final int a = chatModel.users.size();
                aa = chatModel.users.keySet().toArray(new String[a]);

                    FirebaseDatabase.getInstance().getReference("users").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Iterator<DataSnapshot> iter = snapshot.child("refriList").getChildren().iterator();

                                while(iter.hasNext())
                                {
                                    DataSnapshot data = iter.next();

                                    //현재 그룹을 만든 냉장고와 동일한 냉장고 일때
                                    if(data.getKey().equals(snapshot.child("refriList").child(refiename).getKey()))
                                    {
                                        //냉장고 재료들
                                        Iterator<DataSnapshot> iterator = data.getChildren().iterator();
                                        while(iterator.hasNext())
                                        {
                                            DataSnapshot food_data = iterator.next();
                                            Food newFood = food_data.getValue(Food.class);
                                            if(aa!=null)
                                            {
                                                for (int i = 0; i < a; i++) {
                                                    if(!aa[i].equals(myUid)) {

                                                        FirebaseDatabase.getInstance().getReference().child("users").child(aa[i]).child("refriList").child(refiename).child(newFood.getFoodName()).setValue(newFood);

                                                        FirebaseDatabase.getInstance().getReference().child("users").child(aa[i]).child("grouprefriList").child(User.INSTANCE.getRefrigeratorList().
                                                                get(User.INSTANCE.getCurrentRefrigerator()).
                                                                getName()).setValue(chatModel);

                                                    }
                                                }
                                            }

                                        }
                                    }
                                }
                            }

                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



            }


        });
    }


    class SelectFriendRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<User> userModels;

        public SelectFriendRecyclerViewAdapter() {
            userModels = new ArrayList<>();
            final String myUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseDatabase.getInstance().getReference().child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    userModels.clear();

                    for(DataSnapshot snapshot :dataSnapshot.getChildren()){


                        User userModel = snapshot.getValue(User.class);

                        if(userModel.uid.equals(myUid)){
                            continue;
                        }
                        userModels.add(userModel);
                    }
                    notifyDataSetChanged();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_select,parent,false);


            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


            Glide.with
                    (holder.itemView.getContext())
                    .load(userModels.get(position).profileImageUrl)
                    .apply(new RequestOptions().circleCrop())
                    .into(((CustomViewHolder)holder).imageView);
            ((CustomViewHolder)holder).textView.setText(userModels.get(position).UserName);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), MessageActivity.class);
                    intent.putExtra("destinationUid",userModels.get(position).uid);

                    startActivity(intent);


                }
            });

            if(userModels.get(position).comment != null){
                ((CustomViewHolder) holder).textView_comment.setText(userModels.get(position).comment);
            }
            ((CustomViewHolder) holder).checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    //체크 된상태
                    if(b){
                        chatModel.users.put(userModels.get(position).uid,true);

                        //체크 취소 상태
                    }else{
                        chatModel.users.remove(userModels.get(position));
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return userModels.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;
            public TextView textView;
            public TextView textView_comment;
            public CheckBox checkBox;

            public CustomViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.frienditem_imageview);
                textView = (TextView) view.findViewById(R.id.frienditem_textview);
                textView_comment = (TextView)view.findViewById(R.id.frienditem_textview_comment);
                checkBox = (CheckBox)view.findViewById(R.id.friendItem_checkbox);
            }
        }
    }
}
