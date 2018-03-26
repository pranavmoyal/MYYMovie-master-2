package com.example.niraj.mapplication;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by baltech on 15/09/17.
 */

public class Common {
    private static Dialog dialogMapMain;

    public static void showDialog(final Context context, String msg) {
        try {
            View view = LayoutInflater.from(context).inflate(R.layout.common_dialog,
                    null, false);

            if (dialogMapMain != null) {
                dialogMapMain.dismiss();
            }

            try {
                dialogMapMain = new Dialog(context);
                dialogMapMain.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogMapMain.setContentView(view);
                //dialogMapMain.setCancelable(false);
                dialogMapMain.setCanceledOnTouchOutside(true);
                dialogMapMain.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Setting Dialog Title

            TextView ok = (TextView) view.findViewById(R.id.exit_ok);
            ok.setText("OK");
            TextView cancel = (TextView) view.findViewById(R.id.exit_cancel);
            cancel.setVisibility(View.GONE);

            // Setting Dialog Message
            ((TextView) view.findViewById(R.id.msg)).setText(msg);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogMapMain.dismiss();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogMapMain.dismiss();
                }
            });

            dialogMapMain.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
