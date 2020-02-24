package com.example.handleliste;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VareAdapter extends RecyclerView.Adapter<VareAdapter.VareViewHolder> {
    private ArrayList<Vare> dataset;


    public static class VareViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public VareViewHolder(View v){
            super(v);
            textView = v.findViewById(R.id.textView);
        }

    }

    public VareAdapter(ArrayList<Vare> dataset) {
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public VareAdapter.VareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);

        VareViewHolder vh = new VareViewHolder(v);
        return vh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(VareViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(dataset.get(position).getNavn());
        setColor(holder.textView, position);
        final TextView textView = holder.textView;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String navn = textView.getText().toString();
                System.out.println(navn + " in onlongclick");
                downgradeItem(navn, textView);
            }
        });
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //Alertdialog sjekker om vi er sikker på at vi vil slette vare
                dialog(view);


                return true;
            }
        });

    }

    private void dialog(final View view) {
        final TextView textView = (TextView) view;
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Sikker på at du vil slette " + textView.getText().toString() + "?");
        builder.setNegativeButton("Nei", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TextView textView = (TextView) view;
                if(removeItem(textView.getText().toString())){
                    LinearLayout layout = (LinearLayout) view.getParent();
                    layout.removeView(view);
                }
            }
        });
        builder.show();
    }

    private boolean removeItem(String name) {
        boolean removed = false;
        for(int i = 0; i < dataset.size(); i++){
            if(dataset.get(i).getNavn().equals(name)){
                if(removedFromDatabase(dataset.get(i))){
                    dataset.remove(i);
                    removed = true;
                }

            }
        }
        return removed;
    }

    private boolean removedFromDatabase(Vare vare) {
        FirebaseDatabase reff = FirebaseDatabase.getInstance();
        reff.getReference().child("varer").child(vare.getNavn()).removeValue();
        return true;

    }

    private void downgradeItem(String navn, TextView v) {
        int pos = -1;
        for(int i = 0; i < dataset.size(); i++){
            if(dataset.get(i).getNavn().equals(navn)){
                pos = i;
            }
        }
        dataset.get(pos).setStatus(dataset.get(pos).getStatus()+ 1);
        setColor(v, pos);
    }


    private void setColor(TextView textView, int position) {
        if(dataset.get(position).getStatus() == 1 || dataset.get(position).getStatus() > 3){
            textView.setBackgroundResource(R.color.vareGrønn);
            dataset.get(position).setStatus(1);
        }else if(dataset.get(position).getStatus() == 2){
            textView.setBackgroundResource(R.color.vareOrange);
        }else if(dataset.get(position).getStatus() == 3){
            textView.setBackgroundResource(R.color.vareRød);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size();
    }

    private Vare[] defaultItems() {
        Vare melk = new Vare("Melk", 1);
        Vare kaffe = new Vare("Kaffe", 1);
        Vare brød = new Vare("Brød", 2);
        Vare[] dataset = new Vare[3];
        dataset[0] = melk;
        dataset[1] = kaffe;
        dataset[2] = brød;
        return dataset;
    }
}
