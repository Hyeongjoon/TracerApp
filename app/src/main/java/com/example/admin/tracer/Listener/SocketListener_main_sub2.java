package com.example.admin.tracer.Listener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;

import com.example.admin.tracer.Adapter.Main_sub1_list_adapter;
import com.example.admin.tracer.MainSub2Activity;
import com.example.admin.tracer.R;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONObject;

/**
 * Created by admin on 2016-10-13.
 */
public class SocketListener_main_sub2 {
    Activity sub2;
    ProgressDialog pDialog;

    private Emitter.Listener codeResult = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if(null != pDialog){
                pDialog.cancel();
                pDialog = null;
            }
            final String result = (String)args[0];
            final JSONObject groupInfo = (JSONObject)args[1];
            sub2.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(sub2);
                    if(result.equals("true")){
                        RecyclerView asd = (RecyclerView) sub2.findViewById(R.id.main_sub1_group_list);
                        RecyclerView.Adapter temp = asd.getAdapter();
                        if(null!=temp){
                            Log.d("msg" , "여기오냐?/");
                            ((Main_sub1_list_adapter)temp).addGroup(groupInfo);
                            temp.notifyItemInserted(0);
                            asd.scrollToPosition(0);
                        }
                        builder.setTitle(R.string.sub2_add_success);
                    } else if(result.equals("alreadyExist")){
                        builder.setTitle(R.string.sub2_alread_exist);
                    } else if(result.equals("noCode")){
                        builder.setTitle(R.string.sub2_no_code);
                    } else {
                        builder.setTitle(R.string.server_err);
                    }
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }
            });
        }
    };
    public void setActivity(Activity a){
        sub2 = a;
    }
    public void setpDialog(ProgressDialog dialog){
            pDialog = dialog;
    }
    public Emitter.Listener getCodeResultListener() {return codeResult;};
}
