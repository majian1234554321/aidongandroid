package com.leyuan.aidong.adapter.course;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.leyuan.aidong.entity.course.CourseSeat;

/**
 * Created by user on 2017/11/28.
 */
public class CourseChooseSeatIndexAdapter extends RecyclerView.Adapter<CourseChooseSeatIndexAdapter.ViewHoder> {
    Context context;
    CourseSeat seat;

    public CourseChooseSeatIndexAdapter(Context context, CourseSeat seat) {
        this.context = context;
        this.seat = seat;
    }

    @Override
    public ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHoder(LayoutInflater.from(context).inflate(R.layout.item_choose_seat_index, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHoder holder, int position) {
              holder.txtSeat.setText((position+1)+"");
    }

    @Override
    public int getItemCount() {
        if (seat == null)
            return 0;
        return seat.getRow();
    }

    class ViewHoder extends RecyclerView.ViewHolder {

        private  TextView txtSeat;

        public ViewHoder(View itemView) {
            super(itemView);
            txtSeat = (TextView) itemView.findViewById(R.id.txt_seat);
        }
    }
}
