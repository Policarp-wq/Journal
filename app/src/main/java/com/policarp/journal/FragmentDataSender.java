package com.policarp.journal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class FragmentDataSender extends Fragment {
    public interface OnDataSendListener {
        void sendData(Bundle data);
    }
}
