package com.example.imget;

import java.io.File;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends Activity {

    private static final int REQUEST_CODE = 1;
	private Bitmap mBitmap;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
	            intent.setType("image/*");
	            startActivityForResult(Intent.createChooser(intent, "select..."), REQUEST_CODE);
			}
		});
        
        ImageButton s_s = (ImageButton) findViewById(R.id.imageButton1);
        s_s.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(mBitmap == null){
					return;
				}
				else{
					File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
				}
			}
		});
        
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
    		
    		Uri uri = data.getData();
    		Log.d("MainActivity",uri.toString());
    		//Toast.makeText(getApplicationContext(), uri.toString(), Toast.LENGTH_LONG).show();
    		
    		try {
				InputStream stream = getContentResolver().openInputStream(uri);
				Bitmap bm = BitmapFactory.decodeStream(stream);
				stream.close();
				
				if(mBitmap != null){
					mBitmap.recycle();
				}
				mBitmap =Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
				Canvas c = new Canvas(mBitmap);
				c.drawBitmap(bm, 0, 0, null);
				TextPaint tp = new TextPaint();
				tp.setTextSize(bm.getHeight()/3);
				tp.setColor(0x300000ff);
				c.drawText("Arpit", 0, bm.getHeight()/2, tp);
				
				bm.recycle();
				
				ImageView v = (ImageView) findViewById(R.id.imageView1);
				v.setImageBitmap(mBitmap);
			} catch (Exception e) {
				Log.e("MainActivity","decoding BitMap",e);
			}
    	}
    }

}
