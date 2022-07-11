package com.demo.courseworkbank.view.report;

import static com.demo.courseworkbank.utils.Constants.DOB_FORMAT;
import static com.demo.courseworkbank.utils.Constants.OPERATION_SEND;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.demo.courseworkbank.App;
import com.demo.courseworkbank.R;
import com.demo.courseworkbank.model.models.Operation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ReportFragment extends Fragment {

    private static final String ARG_CARD_ID = "arg_card_id";

    private DatePicker dpStartDate;
    private DatePicker dpEndDate;
    private Button btnCreateReport;

    private long cardId;

    public ReportFragment() {

    }

    public static ReportFragment newInstance(long cardId) {
        ReportFragment fragment = new ReportFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_CARD_ID, cardId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardId = getArguments().getLong(ARG_CARD_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dpStartDate = view.findViewById(R.id.dp_start_date);
        dpEndDate = view.findViewById(R.id.dp_end_date);
        btnCreateReport = view.findViewById(R.id.btn_create_report);

        ActivityCompat.requestPermissions(requireActivity(),
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        btnCreateReport.setOnClickListener(v -> {
            Date startDate = getDateFromDatePicker(dpStartDate);
            Date endDate = getDateFromDatePicker(dpEndDate);
            List<Operation> operations = App.getInstance().getDatabase().operationDao().getOperationsByCardId(cardId);
            if (createReport(operations, startDate, endDate)) {
                Toast.makeText(getContext(),
                        "Файл создан: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Файл не создан", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean createReport(List<Operation> operations, Date startDate, Date endDate) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(18);

        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat(DOB_FORMAT);

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "c " + dateFormat.format(startDate) + " до" + dateFormat.format(endDate) + "report.pdf");
        PdfDocument document = new PdfDocument();

        try (FileOutputStream out = new FileOutputStream(file)) {
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(500, operations.size() * 320, 1).create();
            PdfDocument.Page page = document.startPage(pageInfo);
            Canvas c = page.getCanvas();
            float y = 50;
            float xTitle = 50;
            float xObject = 55;
            float step = 40;
            for (Operation item : operations) {
                if (item.getCreateDate().after(startDate) && item.getCreateDate().before(endDate)) {

                    c.drawText("Номер счета:", xTitle, y += step, paint);
                    c.drawText(item.getBillNumber(), xObject, y += step / 2, paint);

                    c.drawText("Операция:", xTitle, y += step, paint);
                    c.drawText(item.getType() + " " + item.getMoney(), xObject, y += step / 2, paint);

                    if (item.getType().equals(OPERATION_SEND)) {
                        c.drawText("Получатель:", xTitle, y += step, paint);
                        c.drawText(item.getMoneyReceiverBillNumber(), xObject, y += step / 2, paint);
                    }

                    c.drawText("Дата:", xTitle, y += step, paint);
                    c.drawText(dateFormat.format(item.getCreateDate()), xObject, y += step / 2, paint);

                    y += step * 2;
                }
            }
            c.save();
            document.finishPage(page);
            document.writeTo(out);
            out.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        return false;
    }

    private Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear() - 1900;
        return new Date(year, month, day);
    }
}