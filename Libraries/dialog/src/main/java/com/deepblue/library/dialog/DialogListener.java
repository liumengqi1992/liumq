package com.deepblue.library.dialog;

import android.app.Dialog;
import android.os.Bundle;

public interface DialogListener {

    Dialog onCreateDialog(Bundle savedInstanceState);

    void onCancel();
}
