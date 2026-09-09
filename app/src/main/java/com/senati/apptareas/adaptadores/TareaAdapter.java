package com.senati.apptareas.adaptadores;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
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
        holder.tvAsignado.setText("Para: " + (tarea.getUsuarioAsignado().isEmpty() ? "Sin asignar" : tarea.getUsuarioAsignado()));
        holder.chipEstado.setText(tarea.getEstado());

        // Cambiar color del chip y texto según estado
        int colorChip, colorTexto, colorCard;
        String estado = tarea.getEstado().toLowerCase();
        
        if (estado.contains("progres")) {
            colorChip = contexto.getColor(R.color.estado_progreso);
            colorTexto = contexto.getColor(R.color.text_estado_progreso);
            colorCard = contexto.getColor(R.color.card_progreso);
        } else if (estado.contains("completada")) {
            colorChip = contexto.getColor(R.color.estado_completada);
            colorTexto = contexto.getColor(R.color.text_estado_completada);
            colorCard = contexto.getColor(R.color.card_completada);
        } else if (estado.contains("no se pudo") || estado.contains("no lograda")) {
            colorChip = contexto.getColor(R.color.estado_fallida);
            colorTexto = contexto.getColor(R.color.text_estado_fallida);
            colorCard = contexto.getColor(R.color.card_fallida);
        } else {
            colorChip = contexto.getColor(R.color.estado_pendiente);
            colorTexto = contexto.getColor(R.color.text_estado_pendiente);
            colorCard = contexto.getColor(R.color.card_pendiente);
        }
        
        holder.chipEstado.setChipBackgroundColor(android.content.res.ColorStateList.valueOf(colorChip));
        holder.chipEstado.setTextColor(colorTexto);
        holder.cardTarea.setCardBackgroundColor(colorCard);

        holder.cbCompletada.setOnCheckedChangeListener(null);
        boolean estaCompletada = tarea.getEstado().equalsIgnoreCase("completada");
        holder.cbCompletada.setChecked(estaCompletada);

        // Tachado si está completada
        if (estaCompletada) {
            holder.tvTitulo.setPaintFlags(holder.tvTitulo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvTitulo.setAlpha(0.5f);
        } else {
            holder.tvTitulo.setPaintFlags(holder.tvTitulo.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.tvTitulo.setAlpha(1.0f);
        }

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
        TextView tvTitulo, tvDescripcion, tvFechaCreacion, tvFechaVencimiento, tvAsignado;
        Chip chipEstado;
        CheckBox cbCompletada;
        ImageButton btnEliminar;
        MaterialCardView cardTarea;

        public TareaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvItemTitulo);
            tvDescripcion = itemView.findViewById(R.id.tvItemDescripcion);
            tvFechaCreacion = itemView.findViewById(R.id.tvItemFechaCreacion);
            tvFechaVencimiento = itemView.findViewById(R.id.tvItemFecha);
            tvAsignado = itemView.findViewById(R.id.tvItemAsignado);
            chipEstado = itemView.findViewById(R.id.chipEstadoItem);
            cbCompletada = itemView.findViewById(R.id.cbCompletada);
            btnEliminar = itemView.findViewById(R.id.btnEliminarTarea);
            cardTarea = itemView.findViewById(R.id.cardTarea);
        }
    }
}