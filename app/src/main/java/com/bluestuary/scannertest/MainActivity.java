package com.bluestuary.scannertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
  private Button scanTrigger;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    scanTrigger = (Button) findViewById(R.id.scan);

    scanTrigger.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        new IntentIntegrator(MainActivity.this).setCaptureActivity(ScanActivity.class).initiateScan();
      }
    });
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

    if(null != result) {
      if(null == result.getContents()) {
        Toast.makeText(this, "Scan annulé", Toast.LENGTH_SHORT).show();
      }else {
        Toast.makeText(this, "Resultat du scan: "+ result.getContents(), Toast.LENGTH_SHORT).show();
      }
    }else
      super.onActivityResult(requestCode, resultCode, data);
  }
}
