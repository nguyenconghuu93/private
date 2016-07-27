package com.vicloud.vn.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.vicloud.vn.MonitorActivity;
import com.vicloud.vn.R;
import com.vicloud.vn.models.Server;
import com.vicloud.vn.models.SheetItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huunc on 7/22/16.
 */
public class ServerAdapter extends RecyclerView.Adapter<ServerAdapter.ServerViewHolder>{
    Activity activity;
    private ArrayList<Server> mDataSet;
    Context context;
    private BottomSheetBehavior mBehavior;
    private View mBottomSheet;
    private BottomSheetDialog mBottomSheetDialog;
    private BottomSheetBehavior mDialogBehavior;
    RecyclerView recyclerView;

    public ServerAdapter(Context context, ArrayList<Server> mDataSet, Activity activity){
        this.activity = activity;
        this.mDataSet = mDataSet;
        this.context = context;
    }

    @Override
    public ServerAdapter.ServerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.server_row, parent, false);
        return new ServerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ServerAdapter.ServerViewHolder holder, int position) {
        final Server server = mDataSet.get(position);
        holder.serverName.setText(server.getServerName());
        holder.ip.setText(server.getIp());
        String text = server.getServerName().toUpperCase();
        holder.icon_entry.setText(String.valueOf(text.substring(0,2)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openServer(server);
            }
        });
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });
        if (server.getVmState().equals("running"))
                holder.cardView.setBackgroundColor(Color.parseColor("#E3F2FD"));
        else if (server.getVmState().equals("suspend"))
            holder.cardView.setBackgroundColor(Color.parseColor("#F4FF81"));
        else
            holder.cardView.setBackgroundColor(Color.parseColor("#FFCDD2"));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
    public static class ServerViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView serverName, ip, icon_entry;
        ImageButton imageButton;
        View overlay;
        ServerViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.server_layout);
            serverName = (TextView) itemView.findViewById(R.id.name_server);
            ip = (TextView) itemView.findViewById(R.id.ip);
            icon_entry = (TextView) itemView.findViewById(R.id.icon_entry);
            imageButton = (ImageButton) itemView.findViewById(R.id.btn_more);
            overlay = itemView.findViewById(R.id.selected_overlay);
        }
    }

    private void openServer(Server server){
        Intent myIntent = new Intent(activity, MonitorActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("server", server);
        myIntent.putExtras(bundle);
        context.startActivity(myIntent);
    }

    @SuppressLint("InflateParams")
    private void showBottomSheetDialog() {
        mBehavior = new BottomSheetBehavior();
        mDialogBehavior = new BottomSheetBehavior();
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        View view = ((Activity)context).getLayoutInflater().inflate(R.layout.sheet_server, null);
//        view.findViewById(R.id.fakeShadow).setVisibility(View.GONE);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewSheet);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(new SheetAdapter(createItems()));

        mBottomSheetDialog = new BottomSheetDialog(context);
        mBottomSheetDialog.setContentView(view);
        mDialogBehavior = BottomSheetBehavior.from((View) view.getParent());

        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });

    }
    public List<SheetItem> createItems() {
        ArrayList<SheetItem> items = new ArrayList<>();
        items.add(new SheetItem(R.drawable.ic_reboot_24dp, "Preview"));
        items.add(new SheetItem(R.drawable.ic_console_24dp, "Preview"));
        items.add(new SheetItem(R.drawable.ic_settings_power_24dp, "Preview"));
        items.add(new SheetItem(R.drawable.ic_info_outline_24dp, "Preview"));
        items.add(new SheetItem(R.drawable.ic_delete_24dp, "Preview"));
        items.add(new SheetItem(R.drawable.ic_reboot_24dp, "Preview"));
        return items;
    }
}
