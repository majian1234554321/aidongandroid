package com.example.aidong.adapter.course;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.aidong.R;
import com.example.aidong .entity.course.CourseSeat;
import com.example.aidong .utils.Logger;
import com.example.aidong .utils.ToastGlobal;

/**
 * Created by user on 2017/11/28.
 */
public class CourseChooseSeatAdapter extends RecyclerView.Adapter<CourseChooseSeatAdapter.ViewHolder> {
    private Context context;
    private CourseSeat seat;
    private int positionSelected = -1;

    public CourseChooseSeatAdapter(Context context, CourseSeat seat, OnItemClickListener listener) {
        this.listener = listener;
        this.context = context;
        this.seat = seat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_choose_seat, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
         String positionSeat = seat.transformePositionToSeatX(position);
      final  String positionSeatS = seat.transformePositionToSeatS(position);

        int state = -1;
//        Logger.i("seat", "positionSeat = " + positionSeat+",positionSelected = " +positionSelected);
        if (seat.getRemoved() != null && seat.getRemoved().contains(positionSeat)) {
            holder.img_seat.setVisibility(View.GONE);
            state = 0;
        } else if (seat.getAppointed() != null && seat.getAppointed().contains(positionSeat)) {
            holder.img_seat.setVisibility(View.VISIBLE);
            holder.img_seat.setImageResource(R.drawable.icon_course_choose_yizhan);
            state = 1;
        } else if (seat.getReserved() != null && seat.getReserved().contains(positionSeat)) {
            holder.img_seat.setVisibility(View.VISIBLE);
            holder.img_seat.setImageResource(R.drawable.icon_course_choose_yizhan);
            state = 1;
        }else {
            holder.img_seat.setVisibility(View.VISIBLE);
            holder.img_seat.setImageResource(R.drawable.icon_course_choose_available);
            state = 2;
        }

        if (position == positionSelected) {
            holder.img_seat.setVisibility(View.VISIBLE);
            holder.img_seat.setImageResource(R.drawable.icon_course_choose_yixuan);
        }

        final int finalState = state;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalState == 2) {

                    notifyItemChanged(positionSelected);

                    positionSelected = position;
                    holder.img_seat.setImageResource(R.drawable.icon_course_choose_yixuan);
                    listener.onSeatChoosed(positionSeatS);
                } else {
                    ToastGlobal.showShortConsecutive("该位置不可选");
                }

                Logger.i("seat", "setOnClickListener = " + positionSeatS + ",positionSelected = " + positionSelected);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (seat == null)
            return 0;
        return seat.getCol() * seat.getRow();
    }

    public void resetChoosedState() {
        notifyItemChanged(positionSelected);

        positionSelected = -1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_seat;

        public ViewHolder(View itemView) {
            super(itemView);
            img_seat = itemView.findViewById(R.id.img_seat);
        }
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onSeatChoosed(String positionSeat);
    }
}
