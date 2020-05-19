package com.nju.ledonghealthhelper.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.nju.ledonghealthhelper.R;

public class LDEditText extends LinearLayout {
    private EditText editText;
    private TextView leftTV;
    private Context context;
    private LayoutInflater layoutInflater;

    public LDEditText(Context context) {
        this(context,null);
    }

    public LDEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LDEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public LDEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context,attrs,defStyleAttr,defStyleRes);
    }

    public void setInputType(int inputType){
        editText.setInputType(inputType);
    }

    @SuppressLint("ResourceAsColor")
    private void initView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        layoutInflater = LayoutInflater.from(getContext());
        Drawable bg = getResources().getDrawable(R.drawable.ld_edit_text_bg,null);
        setBackground(bg);
        setOrientation(LinearLayout.HORIZONTAL);
        setPadding(dp2Px(12),0,dp2Px(12),0);
        leftTV = new TextView(context);
        leftTV.setTextColor(R.color.colorBlack);
        editText = new EditText(context,attrs);
        editText.setBackground(null);
        editText.setMaxLines(1);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setTextColor(R.color.colorBlack);
        editText.setGravity(Gravity.CENTER_VERTICAL);
        editText.setTextSize(sp2px(5));
        LinearLayout.LayoutParams pLeft = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
        pLeft.setMargins(0,0,dp2Px(10),0);
        leftTV.setLayoutParams(pLeft);
        leftTV.setGravity(Gravity.CENTER_VERTICAL);
        LinearLayout.LayoutParams pRight = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        pRight.weight = 1;
        editText.setLayoutParams(pRight);
        editText.setGravity(Gravity.CENTER_VERTICAL);
        editText.setImeOptions(EditorInfo.IME_ACTION_GO);
        editText.setMaxLines(1);
        addView(leftTV);
        addView(editText);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    public void setLeftText(String text)  {
        leftTV.setText(text);
    }

    protected int dp2Px(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    protected int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public String getText() {
        return editText.getText().toString();
    }

    public void  setImeOption(int option) {
        editText.setImeOptions(option);
    }
    public void setText(String text) {
        editText.setText(text);
    }

    public void setEditable(boolean b){
        editText.setFocusable(b);
    }
}
