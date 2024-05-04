package com.policarp.journal;

import android.os.Bundle;
import android.os.Message;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class FragmentDataSender extends Fragment {
    public interface OnDataSendListener {
        void sendMessage(Message msg);
    }
    void showToast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
