package ascione.agata.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ascione.agata.R;
import ascione.agata.service.network.response.ProgettiRecordResponse;
import ascione.agata.service.network.response.ProgettiResponse;
import ascione.agata.service.singleton.ProgettoSingleton;
import ascione.agata.ui.progetto.DettaglioProgettoActivity;
import ascione.agata.utils.Utils;
import ascione.agata.utils.UtilsElem;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{


    private LayoutInflater mInflater;
    private Context context;
    private List<ProgettiRecordResponse> progetti;

    // data is passed into the constructor
    public HomeAdapter(Context context, List<ProgettiRecordResponse> progetti) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.progetti = progetti;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_home, parent, false);
        return new HomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeAdapter.ViewHolder holder, final int position) {

        holder.mTitleTextView.setText(progetti.get(position).getTitle());
        holder.mIconImageView.setImageDrawable(UtilsElem.getIcon(context,Integer.valueOf(progetti.get(position).getIcon())));
        holder.mBackgroundColoredConstraintLayout.setBackground(UtilsElem.getBackgroundIcon(context,Integer.valueOf(progetti.get(position).getMain_color())));
        List<String> users = UtilsElem.getListUsers(progetti.get(position).getId_users());
        setupUsers(users,holder,position);

        holder.mExternalConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgettoSingleton.getInstance().setProgetto(progetti.get(position));
                Intent intent = new Intent(context, DettaglioProgettoActivity.class);
                context.startActivity(intent);
            }
        });

    }


    private void setupUsers(List<String> users,HomeAdapter.ViewHolder holder,int position) {

        holder.mFirstUserTextView.setBackground(UtilsElem.getBackgroundIconNoTop(context,Integer.valueOf(progetti.get(position).getMain_color())));
        holder.mSecondUserTextView.setBackground(UtilsElem.getBackgroundIconNoTop(context,Integer.valueOf(progetti.get(position).getMain_color())));
        holder.mThirdUserTextView.setBackground(UtilsElem.getBackgroundIconNoTop(context,Integer.valueOf(progetti.get(position).getMain_color())));
        holder.mFourthUserTextView.setBackground(UtilsElem.getBackgroundIconNoTop(context,Integer.valueOf(progetti.get(position).getMain_color())));
        holder.mFifthUserTextView.setBackground(UtilsElem.getBackgroundIconNoTop(context,Integer.valueOf(progetti.get(position).getMain_color())));

        switch (users.size()) {
            case 1:
                holder.mFirstUserTextView.setVisibility(View.VISIBLE);
                holder.mFirstUserTextView.setText(Utils.getUser(users.get(0)));
                holder.mSecondUserTextView.setVisibility(View.GONE);
                holder.mThirdUserTextView.setVisibility(View.GONE);
                holder.mFourthUserTextView.setVisibility(View.GONE);
                holder.mFifthUserTextView.setVisibility(View.GONE);
                break;
            case 2:
                holder.mFirstUserTextView.setVisibility(View.VISIBLE);
                holder.mFirstUserTextView.setText(Utils.getUser(users.get(0)));
                holder.mSecondUserTextView.setVisibility(View.VISIBLE);
                holder.mFirstUserTextView.setText(Utils.getUser(users.get(1)));
                holder.mThirdUserTextView.setVisibility(View.GONE);
                holder.mFourthUserTextView.setVisibility(View.GONE);
                holder.mFifthUserTextView.setVisibility(View.GONE);
                break;
            case 3:
                holder.mFirstUserTextView.setVisibility(View.VISIBLE);
                holder.mFirstUserTextView.setText(Utils.getUser(users.get(0)));
                holder.mSecondUserTextView.setVisibility(View.VISIBLE);
                holder.mSecondUserTextView.setText(Utils.getUser(users.get(1)));
                holder.mThirdUserTextView.setVisibility(View.VISIBLE);
                holder.mThirdUserTextView.setText(Utils.getUser(users.get(2)));
                holder.mFourthUserTextView.setVisibility(View.GONE);
                holder.mFifthUserTextView.setVisibility(View.GONE);
                break;
            case 4:
                holder.mFirstUserTextView.setVisibility(View.VISIBLE);
                holder.mFirstUserTextView.setText(Utils.getUser(users.get(0)));
                holder.mSecondUserTextView.setVisibility(View.VISIBLE);
                holder.mSecondUserTextView.setText(Utils.getUser(users.get(1)));
                holder.mThirdUserTextView.setVisibility(View.VISIBLE);
                holder.mThirdUserTextView.setText(Utils.getUser(users.get(2)));
                holder.mFourthUserTextView.setVisibility(View.VISIBLE);
                holder.mFourthUserTextView.setText(Utils.getUser(users.get(3)));
                holder.mFifthUserTextView.setVisibility(View.GONE);
                break;
            case 5:
                holder.mFirstUserTextView.setVisibility(View.VISIBLE);
                holder.mFirstUserTextView.setText(Utils.getUser(users.get(0)));
                holder.mSecondUserTextView.setVisibility(View.VISIBLE);
                holder.mSecondUserTextView.setText(Utils.getUser(users.get(1)));
                holder.mThirdUserTextView.setVisibility(View.VISIBLE);
                holder.mThirdUserTextView.setText(Utils.getUser(users.get(2)));
                holder.mFourthUserTextView.setVisibility(View.VISIBLE);
                holder.mFourthUserTextView.setText(Utils.getUser(users.get(3)));
                holder.mFifthUserTextView.setVisibility(View.VISIBLE);
                holder.mFifthUserTextView.setText(Utils.getUser(users.get(4)));
                break;
            default:
                holder.mFirstUserTextView.setVisibility(View.VISIBLE);
                holder.mFirstUserTextView.setText(Utils.getUser(users.get(0)));
                holder.mSecondUserTextView.setVisibility(View.VISIBLE);
                holder.mSecondUserTextView.setText(Utils.getUser(users.get(1)));
                holder.mThirdUserTextView.setVisibility(View.VISIBLE);
                holder.mThirdUserTextView.setText(Utils.getUser(users.get(2)));
                holder.mFourthUserTextView.setVisibility(View.VISIBLE);
                holder.mFourthUserTextView.setText(Utils.getUser(users.get(3)));
                holder.mFifthUserTextView.setVisibility(View.VISIBLE);
                Integer value = (users.size() - 4);
                holder.mFifthUserTextView.setText("+" + value);
                break;
        }



    }



    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitleTextView,mFirstUserTextView, mSecondUserTextView, mThirdUserTextView, mFourthUserTextView, mFifthUserTextView;
        ConstraintLayout mBackgroundColoredConstraintLayout;
        ImageView mIconImageView;
        ConstraintLayout mExternalConstraintLayout;

        ViewHolder(View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.title);
            mFirstUserTextView = itemView.findViewById(R.id.first_user);
            mSecondUserTextView = itemView.findViewById(R.id.second_user);
            mThirdUserTextView = itemView.findViewById(R.id.third_user);
            mFourthUserTextView = itemView.findViewById(R.id.fourth_user);
            mFifthUserTextView = itemView.findViewById(R.id.fifth_user);
            mBackgroundColoredConstraintLayout = itemView.findViewById(R.id.background_top_color);
            mExternalConstraintLayout = itemView.findViewById(R.id.external_constraint_layout);
            mIconImageView = itemView.findViewById(R.id.icon);
        }

    }





    // total number of cells
    @Override
    public int getItemCount() {
        if (progetti != null){
            return progetti.size();
        }else{
            return 0;
        }
    }

}
