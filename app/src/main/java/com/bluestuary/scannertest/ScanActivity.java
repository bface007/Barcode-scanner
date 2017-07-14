package com.bluestuary.scannertest;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class ScanActivity extends AppCompatActivity implements DecoratedBarcodeView.TorchListener {

  // gestionnaire de capture de scan
  private CaptureManager mCaptureManager;

  // determine si la torche est allumé ou non
  private boolean mIsTorchOn = false;

  private DecoratedBarcodeView mBarcodeView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scan);

    mBarcodeView = (DecoratedBarcodeView) findViewById(R.id.zxing_barcode_scanner);

    // crée un nouvel objet de gestion de capture de scan
    mCaptureManager = new CaptureManager(this, mBarcodeView);
    mCaptureManager.initializeFromIntent(getIntent(), savedInstanceState);

    // commence le décodage de barcode et qrcode
    mCaptureManager.decode();

    mBarcodeView.setTorchListener(this);
  }

  @Override protected void onResume() {
    super.onResume();
    mCaptureManager.onResume();
  }

  @Override protected void onPause() {
    super.onPause();
    mCaptureManager.onPause();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    mCaptureManager.onDestroy();
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mCaptureManager.onSaveInstanceState(outState);
  }

  /**
   * allume/éteint la torche
   */
  private void switchTheLight() {
    if(hasCameraFlashFeature()) {
      if(mIsTorchOn) {
        mBarcodeView.setTorchOff();
      }else {
        mBarcodeView.setTorchOn();
      }
    }
  }

  /**
   * vérifie si l'appareil a des fonctionnalités de torche
   * @return
   */
  private boolean hasCameraFlashFeature() {
    return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
  }

  /**
   * Quand la torche s'allume
   */
  @Override public void onTorchOn() {
    mIsTorchOn = true;
    invalidateOptionsMenu();
  }

  /**
   * Quand la torche s'éteint
   */
  @Override public void onTorchOff() {
    mIsTorchOn = false;
    invalidateOptionsMenu();
  }
}
