package polak.shay.servicenow.shaypolak.view.customLayouts;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import polak.shay.servicenow.shaypolak.R;

public class InputView extends FrameLayout implements View.OnFocusChangeListener, TextWatcher {

    public final static int TYPE_FIRST_NAME = 1;
    public final static int TYPE_LAST_NAME = 2;

    public final static int ALL_DATA = TYPE_FIRST_NAME |
            TYPE_LAST_NAME;

    private final int DEFAULT_DATA_LENGTH = 20;

    private EditText mInput;
    private TextView mType;
    private TextView mLengthData;

    private OnTextChangeListener mDataListener = null;
    private boolean mContainingData = false;
    private Integer mNoFocusColor = null;
    private Integer mWithFocusColor = null;
    private int mTypeData;
    private int mTextLength = 0;
    private int mMaxStringLength = DEFAULT_DATA_LENGTH;


    public InputView(Context context) {
        super(context);
        init(null);
    }

    public InputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray xmlData = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.InputView,
                0, 0);
        init(xmlData);
    }

    public InputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray xmlData = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.InputView,
                0, 0);
        init(xmlData);
    }

    public InputView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray xmlData = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.InputView,
                0, 0);
        init(xmlData);
    }


    private void init(TypedArray xmlData) {
        inflate(getContext(), R.layout.cv_input_view, this);
        mType = findViewById(R.id.type);
        mInput = findViewById(R.id.input);

        mLengthData = findViewById(R.id.length_data);

        if (xmlData != null) {
            String type;
            int id;
            try {
                type = xmlData.getString(R.styleable.InputView_type);
                id = xmlData.getInt(R.styleable.InputView_input_id, 0);
                mInput.setId(id);
            } finally {
                xmlData.recycle();
            }

            if (type != null && !type.isEmpty()) {
                mType.setText(type);

                if (type.equals(getContext().getString(R.string.type_first_name))) {
                    mTypeData = TYPE_FIRST_NAME;
                } else if (type.equals(getContext().getString(R.string.type_last_name))) {
                    mTypeData = TYPE_LAST_NAME;
                }
            }
        }
        mType.setTextColor(getNoFocusColor());
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(mMaxStringLength);
        mInput.setFilters(filterArray);
        mInput.setOnFocusChangeListener(this);
        mInput.addTextChangedListener(this);
        mLengthData.setText("0/" + mMaxStringLength);
    }

//    @Override
//    public Parcelable onSaveInstanceState() {
////        Parcelable superState = super.onSaveInstanceState();
////        SavedState ss = new SavedState(superState);
////        ss.mStateToSave = mStateToSave;
////        return ss;
//    }
//
//    @Override
//    public void onRestoreInstanceState(Parcelable state) {
//        if (!(state instanceof SavedState)) {
//            super.onRestoreInstanceState(state);
//            return;
//        }
//        SavedState ss = (SavedState) state;
//        super.onRestoreInstanceState(ss.getSuperState());
//        mStateToSave = mStateToSave;
//    }

    public void setText(String text) {
        mInput.setText(text);
    }

    public String getText() {
        return mInput.getText().toString();
    }

    private int getNoFocusColor() {
        if (mNoFocusColor == null) {
            mNoFocusColor = new Integer(ContextCompat.getColor(getContext(), R.color.no_focus));
        }
        return mNoFocusColor;
    }

    private int getWithFocusColor() {
        if (mWithFocusColor == null) {
            mWithFocusColor = new Integer(ContextCompat.getColor(getContext(), R.color.with_focus));
        }
        return mWithFocusColor;
    }

    /////////////////////////////////// View.OnFocusChangeListener
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        mType.setTextColor(hasFocus ? getWithFocusColor() : getNoFocusColor());
    }
    //////////////////////////


    ///////////////// TextWatcher
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        mTextLength = s.length();
        mLengthData.setText(mTextLength + "/" + mMaxStringLength);

        if (mDataListener != null) {
            if (mTextLength == 0 && mContainingData) {
                mContainingData = false;
                mDataListener.OnTextChange(mContainingData, mTypeData);
            } else if (mTextLength > 0 && !mContainingData) {
                mContainingData = true;
                mDataListener.OnTextChange(mContainingData, mTypeData);
            }
        }
    }
    /////////////////////////////////////

    public void setOnDataStateChangeListener(OnTextChangeListener listener) {
        mDataListener = listener;
    }

    public interface OnTextChangeListener {
        void OnTextChange(boolean containingData, int type);
    }
}
