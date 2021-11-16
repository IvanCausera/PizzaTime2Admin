package com.example.pizzatime2admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizzatime2admin.modelo.PizzaBebida;

import java.util.ArrayList;

public class PizzaBebidaAdapter extends RecyclerView.Adapter<PizzaBebidaAdapter.PizzaBebidaViewHolder> {
    private Context context;
    private ArrayList<PizzaBebida> list;
    private View.OnClickListener onClickListener;

    public PizzaBebidaAdapter(Context context){
        this.context =  context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public PizzaBebidaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.list_elements, parent, false);
        view.setOnClickListener(this.onClickListener);

        return new PizzaBebidaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaBebidaViewHolder holder, int position) {
        PizzaBebida pizzaBebida = list.get(position);

        holder.txtNombre.setText(pizzaBebida.getNombre());
        holder.txtPrecio.setText(String.valueOf(pizzaBebida.getPrecio()));
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        else return list.size();
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public void changeList(ArrayList<PizzaBebida> list){
        this.list = null;
        this.list = new ArrayList<>();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addToList(ArrayList<PizzaBebida> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addToList(PizzaBebida pizzaBebida){
        this.list.add(pizzaBebida);
        notifyDataSetChanged();
    }

    public void updateItem(int pos, PizzaBebida pizzaBebida){
        this.list.set(pos, pizzaBebida);
        notifyItemChanged(pos);
    }

    public void deleteItem(int pos){
        this.list.remove(pos);
        notifyItemRemoved(pos);
    }

    public ArrayList<PizzaBebida> getList() {
        return list;
    }

    class PizzaBebidaViewHolder extends RecyclerView.ViewHolder{
        TextView txtNombre;
        TextView txtPrecio;

        public PizzaBebidaViewHolder(@NonNull View view) {
            super(view);
            txtNombre = view.findViewById(R.id.txtNombre_listElements);
            txtPrecio = view.findViewById(R.id.txtPrecio_listElements);
        }
    }
}
