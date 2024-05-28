package com.policarp.journal;

import android.os.Bundle;

import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.policarp.journal.adapters.MarksAdapter;
import com.policarp.journal.database.CustomCallBack;
import com.policarp.journal.database.MarkApi;
import com.policarp.journal.database.ServerAPI;
import com.policarp.journal.database.StudentApi;
import com.policarp.journal.database.response.entities.MarkEntity;
import com.policarp.journal.database.response.entities.SchoolParticipantEntity;
import com.policarp.journal.database.response.entities.StudentEntity;
import com.policarp.journal.databinding.FragmentStudentBinding;
import com.policarp.journal.models.School;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import lombok.val;

public class StudentFragment extends FragmentDataSender {
    FragmentStudentBinding binding;

    public SchoolParticipantEntity participant;
    public StudentEntity student;
    OnDataSendListener sendData;
    private StudentApi studentApi;
    private MarkApi markApi;
    private SimpleDateFormat df;
    private Date filterStart, filterEnd;

    public StudentFragment() {
    }

    public static StudentFragment newInstance(SchoolParticipantEntity participant){
        return newInstance(participant, null);
    }
    public static StudentFragment newInstance(SchoolParticipantEntity participant, OnDataSendListener listener) {
        StudentFragment fragment = new StudentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.sendData = listener;
        fragment.participant = participant;
        Log.i(MainActivity.APPTAG, "Created new instance of studentFragment");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studentApi = ServerAPI.getInstance().getStudentApi();
        markApi = ServerAPI.getInstance().getMarkApi();

        df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        GregorianCalendar calendar = new GregorianCalendar();
        filterStart = new GregorianCalendar(calendar.get(Calendar.YEAR) - 1, 8, 1).getTime();
        filterEnd = new GregorianCalendar().getTime();
        Log.i(MainActivity.APPTAG, "Created student fragment");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStudentBinding.inflate(inflater, container, false);
        String selectedRange = df.format(filterStart) + " - " + df.format(filterEnd);
        binding.selectedDateRange.setText(selectedRange);
        CustomCallBack<List<MarkEntity>> markCallBack = new CustomCallBack<>(
                (c, r)->{
                    ArrayList<MarkEntity> arrayList = new ArrayList<>(r.body());
                    MarksAdapter adapter = new MarksAdapter(arrayList);
                    binding.subjects.setAdapter(adapter);
                    binding.subjects.setLayoutManager(new LinearLayoutManager(getContext()));
                    applyFilter();
                },null,null
        );
        CustomCallBack<StudentEntity> callBack = new CustomCallBack<>(
                (c, r)->{
                    student = r.body();
                    markApi.getStudentsMarks(student.getStudentId()).enqueue(markCallBack);
                }, null, null
        );
        studentApi.getStudentByParticipantId(participant.getParticipantId())
                .enqueue(callBack);
        binding.selectedDateRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        binding.selectedSubjectName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilter();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Log.i(MainActivity.APPTAG, "Created view for student fragment");
        return binding.getRoot();
    }

    private void showDatePicker() {
        LocalDateTime local = LocalDateTime.of(2024, 1, 1, 0, 0);
        Long st = local.atZone(ZoneId.ofOffset("UTC", ZoneOffset.UTC)).toInstant().toEpochMilli();
        CalendarConstraints c = new CalendarConstraints.Builder()
                .setStart(st)
                .build();
        MaterialDatePicker<Pair<Long, Long>> datePicker = MaterialDatePicker
                .Builder
                .dateRangePicker()
                .setTitleText("Выберите период просмотра оценок")
                .setCalendarConstraints(c)
                .build();
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> longLongPair) {
                Long startDate = longLongPair.first;
                Long endDate = longLongPair.second;

                filterStart = new Date(startDate);
                filterEnd = new Date(endDate);
                filterStart.setHours(0);
                filterEnd.setHours(23);
                filterEnd.setMinutes(59);
                String selectedRange = df.format(filterStart) + " - " + df.format(filterEnd);
                binding.selectedDateRange.setText(selectedRange);
                applyFilter();
            }
        });
        datePicker.show(getParentFragmentManager(), "DATE_PICKER");
    }
    private void applyFilter(){
        String subjName = binding.selectedSubjectName.getText().toString();
        ((MarksAdapter)binding.subjects.getAdapter())
                .filter(subjName, filterStart, filterEnd);
        double average = ((MarksAdapter)binding.subjects.getAdapter()).getAverage();
        binding.averageFilteredMarks.setText(String.format("%,.2f", average));
        binding.averageFilteredMarks.setTextColor(MarksAdapter.getMarkColor((int)Math.round(average)));
    }
}