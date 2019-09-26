package com.example.mybookstore.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mybookstore.Models.ModelComment;
import com.example.mybookstore.R;
import com.example.mybookstore.Utils.Links;
import com.squareup.picasso.Picasso;
import com.willy.ratingbar.ScaleRatingBar;

import java.lang.reflect.Type;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.viewHolder> {
    Context context;
    List<ModelComment> modelComments;

    public AdapterComment(Context context, List<ModelComment> modelComments) {
        this.context = context;
        this.modelComments = modelComments;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_show_comments,viewGroup,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {

        ModelComment comment = modelComments.get(i);

        viewHolder.txtUser.setText(comment.getUser());
        viewHolder.txtPositive.setText(comment.getPositive());
        viewHolder.txtNegative.setText(comment.getNegative());
        viewHolder.txtComment.setText(comment.getComment());

        Picasso.with(context).load(comment.getImage().replace(Links.LOCALHOST,Links.LINK_ADAPTER))
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.circleImageView);
        viewHolder.ratingBar.setRating(comment.getRating());

    }

    @Override
    public int getItemCount() {
        return modelComments.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{

        TextView txtComment,txtUser,txtPositive,txtNegative;
        CircleImageView circleImageView;
        ScaleRatingBar ratingBar;

        viewHolder(@NonNull View itemView) {
            super(itemView);
            txtComment=itemView.findViewById(R.id.txt_commnet);
            txtNegative=itemView.findViewById(R.id.txt_negativeComment);
            txtPositive=itemView.findViewById(R.id.txt_posotiveComment);
            txtUser=itemView.findViewById(R.id.txt_usercomment);
            circleImageView = itemView.findViewById(R.id.img_userComment);
            ratingBar = itemView.findViewById(R.id.rating_comment);
        }
    }
}
