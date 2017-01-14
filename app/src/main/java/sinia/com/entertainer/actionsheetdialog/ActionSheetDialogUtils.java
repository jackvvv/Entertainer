package sinia.com.entertainer.actionsheetdialog;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import sinia.com.entertainer.actionsheetdialog.ActionSheetDialog.OnSheetItemClickListener;
import sinia.com.entertainer.actionsheetdialog.ActionSheetDialog.SheetItemColor;

public class ActionSheetDialogUtils {


    public static void createSexDialog(Context context, final TextView tv) {
        new ActionSheetDialog(context)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("男", SheetItemColor.BLACK,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv.setText("男");
                                tv.setTextColor(Color.parseColor("#313131"));
                            }
                        })
                .addSheetItem("女", SheetItemColor.BLACK,
                        new OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                tv.setText("女");
                                tv.setTextColor(Color.parseColor("#313131"));
                            }
                        }).show();
    }

}
