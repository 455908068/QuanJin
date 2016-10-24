package com.xqkj.quanjin.base;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;

import com.xqkj.quanjin.R;


/**
 * 加载进度对话框
 */
public class ProgressDialogFragment extends BaseDialog {

//    private View view;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), getTheme());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_progress_dialog);
//        dialog.setCancelable(false);
        setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    /**此方法连续点击时会出现崩溃现象，测试界面为我的车辆*/
/*    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == view) {
            view = View.inflate(getContext(), R.layout.fragment_progress_dialog, null);
            //设置背景透明
            if (getDialog() != null && getDialog().getWindow() != null) {
                getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            setCancelable(false);
//            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        }
        return view;
    }*/


}
