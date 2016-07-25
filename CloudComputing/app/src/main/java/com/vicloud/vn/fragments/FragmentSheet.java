package com.vicloud.vn.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.vicloud.vn.R;
import com.vicloud.vn.adapters.SheetAdapter;
import com.vicloud.vn.models.SheetItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huunc on 7/25/2016.
 */
public class FragmentSheet extends BottomSheetDialogFragment {
    private BottomSheetBehavior mBehavior;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        View view = View.inflate(getContext(), R.layout.sheet_row, null);
        view.findViewById(R.id.fakeShadow).setVisibility(View.GONE);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SheetAdapter itemAdapter = new SheetAdapter(createItems());
        recyclerView.setAdapter(itemAdapter);

        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    public List<SheetItem> createItems() {
        ArrayList<SheetItem> items = new ArrayList<>();
        items.add(new SheetItem(R.drawable.ic_reboot_24dp, "Preview"));
        return items;
    }
}
