package com.senati.apptareas.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.Chip;
import com.senati.apptareas.R;
import com.senati.apptareas.entidades.Tarea;
import java.util.List;

public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.TareaViewHolder> {

    private List<Tarea> listaTareas;
    private Context contexto;
    private OnTareaListener listener;

    // Interfaz para comunicar los clics al MainActivity
    public interface OnTareaListener {
        void onEditarClick(Tarea tarea);
        void onEliminarClick(Tarea tarea);
        void onEstadoCambio(Tarea tarea, boolean isChecked);
    }

    public TareaAdapter(Context contexto, List<Tarea> listaTareas, OnTareaListener listener) {
        this.contexto = contexto;
        this.listaTareas = listaTareas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contexto).inflate(R.layout.item_tarea, parent, false);
        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        Tarea tarea = listaTareas.get(position);

        // Asignar los textos requeridos
        holder.tvTitulo.setText(tarea.getTitulo());
        holder.tvDescripcion.setText(tarea.getDescripcion());
        holder.tvFechaCreacion.setText("Creado: " + tarea.getFechaCreacion());
        holder.tvFechaVencimiento.setText("Vence: " + tarea.getFechaVencimiento());
        holder.chipEstado.setText(tarea.getEstado());

        // Quitar el listener temporalmente al reciclar la vista para evitar falsos positivos
        holder.cbCompletada.setOnCheckedChangeListener(null);
        holder.cbCompletada.setChecked(tarea.getEstado().equalsIgnoreCase("completada"));

        // Evento: Cambiar estado desde el CheckBox
        holder.cbCompletada.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (listener != null) listener.onEstadoCambio(tarea, isChecked);
        });

        // Evento: Eliminar tarea
        holder.btnEliminar.setOnClickListener(v -> {
            if (listener != null) listener.onEliminarClick(tarea);
        });

        // Evento: Tocar la tarjeta completa para ir a Editar
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onEditarClick(tarea);
        });
    }

    @Override
    public int getItemCount() {
        return listaTareas.size();
    }

    // Método para refrescar el RecyclerView cuando se aplican filtros
    public void actualizarLista(List<Tarea> nuevaLista) {
        this.listaTareas = nuevaLista;
        notifyDataSetChanged();
    }

    public static class TareaViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvDescripcion, tvFechaCreacion, tvFechaVencimiento;
        Chip chipEstado;
        CheckBox cbCompletada;
        ImageButton btnEliminar;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvItemTitulo);
            tvDescripcion = itemView.findViewById(R.id.tvItemDescripcion);
            tvFechaCreacion = itemView.findViewById(R.id.tvItemFechaCreacion);
            tvFechaVencimiento = itemView.findViewById(R.id.tvItemFecha);
            chipEstado = itemView.findViewById(R.id.chipEstadoItem);
            cbCompletada = itemView.findViewById(R.id.cbCompletada);
            btnEliminar = itemView.findViewById(R.id.btnEliminarTarea);
        }
    }
}