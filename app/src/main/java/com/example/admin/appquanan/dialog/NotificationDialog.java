package com.example.admin.appquanan.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Admin on 10/19/2017.
 */

public class NotificationDialog extends DialogFragment{
    public interface NotificationDialogFragmentListener {
        void onClickYes(int which);
        void onClickNo(int which);
    }

    private NotificationDialog.NotificationDialogFragmentListener _listener;

    private String _strTitle;
    private String _strMessage;
    private String _strPositive;
    private String _strNegative;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (null != _strTitle) {
            builder.setTitle(_strTitle);
        }

        if (null != _strMessage) {
            builder.setMessage(_strMessage);
        }

        if (null != _strPositive) {
            builder.setPositiveButton(_strPositive, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (null != _listener) {
                        _listener.onClickYes(which);
                    }
                }
            });
        }

        if (null != _strNegative) {
            builder.setNegativeButton(_strNegative, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (null != _listener) {
                        _listener.onClickNo(which);
                    }
                }
            });
        }

        return builder.create();
    }

    public void setListener(NotificationDialog.NotificationDialogFragmentListener listener) {
        _listener = listener;
    }

    public void setTitle(String strTitle) {
        _strTitle = strTitle;
    }

    public void setMessage(String strMessage) {
        _strMessage = strMessage;
    }

    public void setPositiveLabel(String strLabel) {
        _strPositive = strLabel;
    }

    public void setNegativeLabel(String strLabel) {
        _strNegative = strLabel;
    }
}
