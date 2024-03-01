package com.mozark.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mozark.todoapp.model.Response;
import com.mozark.todoapp.model.Result;
import com.squareup.picasso.Picasso;

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.ViewHolder> {
    private final Context context;

    private onClickListener listener;

    public Response getResponse() {
        return response;
    }

    private com.mozark.todoapp.model.Response response;

    public RecylerViewAdapter(Response responseList, Context context,onClickListener listener) {
        this.response = responseList;
        this.context= context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecylerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.rv_list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecylerViewAdapter.ViewHolder holder, int position) {
        Result result = response.getResults().get(position);
        //TODO: Later on Add Formatter for The Date of the DOB
        holder.getTvBirthdate().setText(result.getDob().getDate());
        holder.getTvUsername().setText(renderName(result));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });
        Picasso.get().load(result.getPicture().getThumbnail()).into(holder.getTvUserImage());

    }

    private String renderName(Result result) {
        String title = result.getName().getTitle();
        String first = result.getName().getFirst();
        String last = result.getName().getLast();
        return title + " " + first + " " + last;
    }

    @Override
    public int getItemCount() {
        return response ==null?-1: response.getResults().size();
    }

    public void setListener(onClickListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView tvUserImage;
        TextView tvBirthdate;
        TextView tvUsername;

        public ImageView getTvUserImage() {
            return tvUserImage;
        }

        public void setTvUserImage(ImageView tvUserImage) {
            this.tvUserImage = tvUserImage;
        }

        public TextView getTvBirthdate() {
            return tvBirthdate;
        }

        public void setTvBirthdate(TextView tvBirthdate) {
            this.tvBirthdate = tvBirthdate;
        }

        public TextView getTvUsername() {
            return tvUsername;
        }

        public void setTvUsername(TextView tvUsername) {
            this.tvUsername = tvUsername;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvBirthdate = itemView.findViewById(R.id.tv_birthdate);
            this.tvUsername = itemView.findViewById(R.id.tv_username);
            this.tvUserImage = itemView.findViewById(R.id.iv_user);
        }
    }

    interface onClickListener{
        void onClick(int position);
    }
}
