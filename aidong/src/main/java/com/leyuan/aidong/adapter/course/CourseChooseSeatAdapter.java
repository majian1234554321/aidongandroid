package com.leyuan.aidong.adapter.course;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leyuan.aidong.R;
import com.leyuan.aidong.entity.course.CourseSeat;
import com.leyuan.aidong.utils.Logger;
import com.leyuan.aidong.utils.ToastGlobal;

/**
 * Created by user on 2017/11/28.
 */
public class CourseChooseSeatAdapter extends RecyclerView.Adapter<CourseChooseSeatAdapter.ViewHoder> {
    Context context;
    CourseSeat seat;
    int positionSelected = -1;

    public CourseChooseSeatAdapter(Context context, CourseSeat seat, OnItemClickListener listener) {
        this.listener = listener;
        this.context = context;
        this.seat = seat;
    }

    @Override
    public ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHoder(LayoutInflater.from(context).inflate(R.layout.item_choose_seat, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHoder holder, final int position) {
        final String positionSeat = seat.transformePositionToSeat(position);

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
                    listener.onSeatChoosed(positionSeat);
                } else {
                    ToastGlobal.showShortConsecutive("该位置不可选");
                }

                Logger.i("seat", "setOnClickListener = " + positionSeat + ",positionSelected = " + positionSelected);
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

    class ViewHoder extends RecyclerView.ViewHolder {
        ImageView img_seat;

        public ViewHoder(View itemView) {
            super(itemView);
            img_seat = (ImageView) itemView.findViewById(R.id.img_seat);
        }
    }

    OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onSeatChoosed(String positionSeat);
    }
}
