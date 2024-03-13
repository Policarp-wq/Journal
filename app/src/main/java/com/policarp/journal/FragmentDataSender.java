package com.policarp.journal;

import android.os.Bundle;
import android.os.Message;

import androidx.fragment.app.Fragment;

public class FragmentDataSender extends Fragment {
    public interface OnDataSendListener {
        void sendMessage(Message msg);
    }
}
