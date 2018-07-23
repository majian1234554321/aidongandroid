package com.example.aidong.adapter.course;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aidong.R;
import com.example.aidong .entity.course.CourseSeat;

/**
 * Created by user on 2017/11/28.
 */
public class CourseChooseSeatIndexAdapter extends RecyclerView.Adapter<CourseChooseSeatIndexAdapter.ViewHolder> {
    private Context context;
    private CourseSeat seat;

    public CourseChooseSeatIndexAdapter(Context context, CourseSeat seat) {
        this.context = context;
        this.seat = seat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_choose_seat_index, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
              holder.txtSeat.setText((position+1)+"");
    }

    @Override
    public int getItemCount() {
        if (seat == null)
            return 0;
        return seat.getRow();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private  TextView txtSeat;

        public ViewHolder(View itemView) {
            super(itemView);
            txtSeat =  itemView.findViewById(R.id.txt_seat);
        }
    }
}
