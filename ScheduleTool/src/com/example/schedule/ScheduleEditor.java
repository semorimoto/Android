package com.example.schedule;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScheduleEditor extends Activity implements OnCheckedChangeListener, OnClickListener {

	private EditText edtTitle; // タイトル
	private EditText edtBody; // 本文
	private EditText edtYear; // 年
	private EditText edtMonth; // 月
	private EditText edtDay; // 日
	private EditText edtHour; // 時
	private EditText edtMinutes; // 分

	private CheckBox chkAlarm; // アラーム指定

	private Button btnAdd; // スケジュール追加
	private Button btnDel; // スケジュール削除

	// 画面最大幅で表示
	private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;
	// 必要な値で表示
	private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLayout();
	}

	/**
	 * レイアウトの設定
	 */
	private void setLayout() {
		// ----------------------
		// メインレイアウトの生成
		// ----------------------
		LinearLayout layout = new LinearLayout(this);
		setContentView(layout);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setGravity(Gravity.LEFT);

		//update.

		// b
		// c

		// タイトルエディットの生成
		layout.addView(makeLabel("タイトル"));
		edtTitle = makeEditor("", MP, WC);
		layout.addView(edtTitle);

		// 本文エディットの生成
		layout.addView(makeLabel("本文"));
		edtBody = makeEditor("", MP, 200);
		edtBody.setSingleLine(false);
		layout.addView(edtBody);

		// アラームチェックボックス
		chkAlarm = new CheckBox(this);
		chkAlarm.setOnCheckedChangeListener(this);
		chkAlarm.setText("指定時刻にアラームを鳴らす");
		layout.addView(chkAlarm);


		// ------------------------
		// 日付用のレイアウトの生成
		// ------------------------
		LinearLayout layoutDate = new LinearLayout(this);
		layoutDate.setOrientation(LinearLayout.HORIZONTAL);

		// 年エディットの生成
		edtYear = makeNumericEditor("", WC, WC, 200, 4);
		layoutDate.addView(makeLabel("年"));
		layoutDate.addView(edtYear);

		// 月エディットの生成
		edtMonth = makeNumericEditor("", WC, WC, 100, 2);
		layoutDate.addView(makeLabel("月"));
		layoutDate.addView(edtMonth);

		// 日エディットの生成
		edtDay = makeNumericEditor("", WC, WC, 100, 2);
		layoutDate.addView(makeLabel("日"));
		layoutDate.addView(edtDay);

		// メインレイアウトに追加
		layout.addView(layoutDate);


		// ------------------------
		// 時間用のレイアウトの生成
		// ------------------------
		LinearLayout layoutMinutes = new LinearLayout(this);
		layoutMinutes.setOrientation(LinearLayout.HORIZONTAL);

		// 時エディットの生成
		layoutMinutes.addView(makeLabel("時"));
		edtHour = makeNumericEditor("", WC, WC, 100, 2);
		layoutMinutes.addView(edtHour);

		// 分エディットの生成
		layoutMinutes.addView(makeLabel("分"));
		edtMinutes = makeNumericEditor("", WC, WC, 100, 2);
		layoutMinutes.addView(edtMinutes);

		// メインレイアウトに追加
		layout.addView(layoutMinutes);


		// ------------------------
		// ボタン用レイアウトの生成
		// ------------------------
		LinearLayout layoutBtn = new LinearLayout(this);
		layoutBtn.setOrientation(LinearLayout.HORIZONTAL);

		// 追加ボタン
		btnAdd = new Button(this);
		btnAdd.setText("スケジュール登録");
		btnAdd.setOnClickListener(this);
		layoutBtn.addView(btnAdd);

		// 削除ボタン
		btnDel = new Button(this);
		btnDel.setText("スケジュール削除");

		btnDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 削除ボタンの押下時にダイアログを表示する
				showDialog(ScheduleEditor.this, "", "スケジュール削除ボタンクリック１");
			}
		});
		layoutBtn.addView(btnDel);

		// メインレイアウトに追加
		layout.addView(layoutBtn);
	}

	/**
	 * ラベルの生成
	 * @param text 表示する文字列
	 * @return TextViewオブジェクト
	 */
	private TextView makeLabel(String text) {
		TextView label = new TextView(this);
		label.setText(text);
		label.setLayoutParams(new LinearLayout.LayoutParams(WC,WC));
		return label;
	}

	/**
	 * エディタの生成
	 * @param text 表示する文字列
	 * @param w 横幅
	 * @param h 縦幅
	 * @return EditTextオブジェクト
	 */
	private EditText makeEditor(String text, int w, int h) {
		// EditTextオブジェクトの生成
		EditText editor = new EditText(this);
		editor.setText(text);
		editor.setGravity(Gravity.LEFT|Gravity.TOP);
		editor.setHorizontallyScrolling(true); // 必要に応じて縦スクロール表示
		editor.setLayoutParams(new LinearLayout.LayoutParams(w, h));
		return editor;
	}

	/**
	 * エディタの生成
	 * @param text 表示する文字列
	 * @param w 横幅
	 * @param h 縦幅
	 * @return EditTextオブジェクト
	 */
	private EditText makeNumericEditor(String text, int w, int h, int width, int maxlen) {
		// EditTextオブジェクトの生成
		EditText editor = new EditText(this);
		editor.setText(text);
		editor.setGravity(Gravity.LEFT|Gravity.TOP);
		editor.setHorizontallyScrolling(true); // 必要に応じて縦スクロール表示
		editor.setLayoutParams(new LinearLayout.LayoutParams(w,h));
		editor.setWidth(width);
		editor.setFilters(setMaxlength(maxlen));
		editor.setInputType(InputType.TYPE_CLASS_NUMBER);
		return editor;
	}

	/**
	 * 最大桁数を設定する
	 * @param maxlen 最大桁数
	 * @return 最大桁数の設定されたInputFilter配列
	 */
	private InputFilter[] setMaxlength(int maxlen) {
			return new InputFilter[] {
					new InputFilter.LengthFilter(maxlen)
			};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.schedule_editor, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
		if (view == btnAdd) {
			// 追加ボタン押下時のダイアログを表示
			showDialog(this, "", "スケジュール追加ボタンクリック");
		} else if (view == btnDel) {
			// 削除ボタン押下時のダイアログを表示
			showDialog(this, "", "スケジュール削除ボタンクリック２");
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton view, boolean isChecked) {
		//
		if (view == chkAlarm) {
			showDialog(this, "", "アラーム起動ボタン：" + isChecked);
		}
	}

	public void showDialog(final Activity activity, String title, String text) {
		// ダイアログの作成
		Builder ad = new AlertDialog.Builder(activity);
		ad.setTitle(title);
		ad.setMessage(text);
		ad.setPositiveButton("ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// OKボタンクリックの処理
			}
		});
		ad.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// キャンセルボタンクリックの処理
			}
		});
		// ダイアログの表示
		ad.create();
		ad.show();
	}

}
