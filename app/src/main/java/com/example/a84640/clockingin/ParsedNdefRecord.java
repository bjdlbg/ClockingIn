package com.example.a84640.clockingin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface ParsedNdefRecord {
    public View getView(Activity activity, LayoutInflater inflater, ViewGroup parent, int offset);
}
