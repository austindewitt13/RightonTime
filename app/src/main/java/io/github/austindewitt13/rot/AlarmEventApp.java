package io.github.austindewitt13.rot;

import android.app.Application;
import com.facebook.stetho.Stetho;
import io.github.austindewitt13.rot.service.GoogleSignInService;

public class AlarmEventApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    GoogleSignInService.setContext(this);
  }

}
